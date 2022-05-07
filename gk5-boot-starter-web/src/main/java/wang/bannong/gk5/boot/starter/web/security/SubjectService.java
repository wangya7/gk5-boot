package wang.bannong.gk5.boot.starter.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;

public class SubjectService implements UserDetailsService {
    /**
     * 查询登录主体信息
     *
     * @param principal          登录标识； 手机号码/用户名
     * @param password           登录密码/验证码
     * @param authenticationType 验证方式
     * @param subjectClient      客户端
     */
    public Subject querySubject(String principal,
                                String password,
                                AuthenticationType authenticationType,
                                SubjectClient subjectClient) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
