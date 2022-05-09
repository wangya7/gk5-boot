package wang.bannong.gk5.boot.starter.web.security.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import wang.bannong.gk5.boot.starter.web.gateway.Request1stFilter;
import wang.bannong.gk5.boot.starter.web.security.AuthenticationEntryPoint;
import wang.bannong.gk5.boot.starter.web.security.ConvergenceAuthenticationProvider;
import wang.bannong.gk5.boot.starter.web.security.SubjectService;
import wang.bannong.gk5.boot.starter.web.security.annotation.Anonymous;
import wang.bannong.gk5.boot.starter.web.security.annotation.PermitAll;
import wang.bannong.gk5.boot.starter.web.security.filter.LogoutSuccessHandler;
import wang.bannong.gk5.boot.starter.web.util.SpringUtils;

/**
 * @author bn
 * @date 2022/5/5
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private Request1stFilter request1stFilter;
    @Resource
    private SubjectService subjectService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        // 查找匿名标记URL
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = applicationContext
                .getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        Set<String> permitAllUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            PathPatternsRequestCondition condition = entry.getKey().getPathPatternsCondition();
            Set<PathPattern> urls = condition.getPatterns();
            if (CollectionUtils.isNotEmpty(urls)) {
                Set<String> urlSet = urls.stream()
                                         .map(PathPattern::getPatternString)
                                         .collect(Collectors.toSet());
                // 注意 PermitAll 的优先级是高于 Anonymous
                PermitAll permitAll = handlerMethod.getMethod().getAnnotation(PermitAll.class);
                if (permitAll != null) {
                    permitAllUrls.addAll(urlSet);
                } else {
                    Anonymous anonymousAccess =
                            handlerMethod.getMethod().getAnnotation(Anonymous.class);
                    if (anonymousAccess != null) {
                        anonymousUrls.addAll(urlSet);
                    }
                }
            }
        }
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 过滤请求
                .authorizeRequests()
                // 匿名访问，仅允许匿名用户访问,如果登录认证后，带有token信息再去请求，这个anonymous()关联的资源就不能被访问
                .antMatchers(anonymousPath()).anonymous()
                .antMatchers(anonymousUrls.toArray(new String[0])).anonymous()
                // 永远都能访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**").permitAll()
                .antMatchers(permitAllUrls.toArray(new String[0])).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        httpSecurity.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler);

        httpSecurity.addFilterBefore(request1stFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public static String[] anonymousPath() {
        return new String[] {
                "/druid/**", "/webjars/**",
                "/*/api-docs", "/swagger-resources/**", "/swagger-ui.html",
                "/profile/**", "/common/download**", "/common/download/resource**",
                "/login", "/register", "/captchaImage", "/sendSms", "/ntm",
                "/upload/**", "/count/export/**", "/report/earning", "/report/package",
                "/file/download", "/file/upload/img", "/salary/real/time/import",
                "/sale/insurance/day/upload/dayupload", "/test/v1/**",
        };
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证管理
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(convergenceAuthenticationProvider());
        auth.userDetailsService(subjectService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationProvider convergenceAuthenticationProvider(){
        return new ConvergenceAuthenticationProvider(subjectService);
    }
}
