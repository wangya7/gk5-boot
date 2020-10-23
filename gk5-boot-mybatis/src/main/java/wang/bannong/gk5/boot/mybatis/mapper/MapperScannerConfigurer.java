package wang.bannong.gk5.boot.mybatis.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import wang.bannong.gk5.boot.mybatis.MybatisXConstant;
import wang.bannong.gk5.boot.mybatis.db.DataSourcePrefix;
import wang.bannong.gk5.boot.mybatis.db.DbProperties;

/**
 * BeanDefinitionRegistryPostProcessor that searches recursively starting from a base package for
 * interfaces and
 * registers them as {@code MapperFactoryBean}. Note that only interfaces with at least one method
 * will be registered;
 * concrete classes will be ignored.
 * <p>
 * This class was a {code BeanFactoryPostProcessor} until 1.0.1 version. It changed to
 * {@code BeanDefinitionRegistryPostProcessor} in 1.0.2. See https://jira.springsource.org/browse/SPR-8269
 * for the
 * details.
 * <p>
 * The {@code basePackage} property can contain more than one package name, separated by either
 * commas or semicolons.
 * <p>
 * This class supports filtering the mappers created by either specifying a marker interface or an
 * annotation. The
 * {@code annotationClass} property specifies an annotation to search for. The {@code
 * markerInterface} property
 * specifies a parent interface to search for. If both properties are specified, mappers are added
 * for interfaces that
 * match <em>either</em> criteria. By default, these two properties are null, so all interfaces in
 * the given
 * {@code basePackage} are added as mappers.
 * <p>
 * This configurer enables autowire for all the beans that it creates so that they are
 * automatically
 * autowired with the
 * proper {@code SqlSessionFactory} or {@code SqlSessionTemplate}. If there is more than one {@code
 * SqlSessionFactory}
 * in the application, however, autowiring cannot be used. In this case you must explicitly specify
 * either an
 * {@code SqlSessionFactory} or an {@code SqlSessionTemplate} to use via the <em>bean name</em>
 * properties. Bean names
 * are used rather than actual objects because Spring does not initialize property placeholders
 * until after this class
 * is processed.
 * <p>
 * Passing in an actual object which may require placeholders (i.e. DB user password) will fail.
 * Using bean names defers
 * actual object creation until later in the startup process, after all placeholder substitution is
 * completed. However,
 * note that this configurer does support property placeholders of its <em>own</em> properties. The
 * <code>basePackage</code> and bean name properties all support <code>${property}</code> style
 * substitution.
 * <p>
 * Configuration sample:
 *
 * <pre class="code">
 * {@code
 *   <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 *       <property name="basePackage" value="org.mybatis.spring.sample.mapper" />
 *       <!-- optional unless there are multiple session factories defined -->
 *       <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
 *   </bean>
 * }
 * </pre>
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 * @see MapperFactoryBean
 * @see ClassPathMapperScanner
 */
@Component
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor,
        ApplicationContextAware, BeanNameAware {

    private static final Logger log = LogManager.getLogger(MapperScannerConfigurer.class);

    private static String             mappersPath;
    private static String             primary;
    private static List<DbProperties> masters = new ArrayList<>();
    private static List<DbProperties> slaves  = new ArrayList<>();

    public static String getMappersPath() {
        return mappersPath;
    }

    public static String getPrimary() {
        return primary;
    }

    public static List<DbProperties> getMasters() {
        return masters;
    }

    public static List<DbProperties> getSlaves() {
        return slaves;
    }

    private String basePackage;

    private boolean addToConfig = true;

    private String lazyInitialization;

    private String sqlSessionFactoryBeanName;

    private String sqlSessionTemplateBeanName;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends MapperFactoryBean> mapperFactoryBeanClass;

    private ApplicationContext applicationContext;

    private String beanName;

    private boolean processPropertyPlaceHolders;

    private BeanNameGenerator nameGenerator;

    /**
     * This property lets you set the base package for your mapper interface files.
     * <p>
     * You can set more than one package by using a semicolon or comma as a separator.
     * <p>
     * Mappers will be searched for recursively starting in the specified package(s).
     *
     * @param basePackage base package name
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Same as {@code MapperFactoryBean#setAddToConfig(boolean)}.
     *
     * @param addToConfig a flag that whether add mapper to MyBatis or not
     * @see MapperFactoryBean#setAddToConfig(boolean)
     */
    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * Set whether enable lazy initialization for mapper bean.
     * <p>
     * Default is {@code false}.
     * </p>
     *
     * @param lazyInitialization Set the @{code true} to enable
     * @since 2.0.2
     */
    public void setLazyInitialization(String lazyInitialization) {
        this.lazyInitialization = lazyInitialization;
    }

    /**
     * This property specifies the annotation that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have the specified
     * annotation.
     * <p>
     * Note this can be combined with markerInterface.
     *
     * @param annotationClass annotation class
     */
    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    /**
     * This property specifies the parent that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have the specified
     * interface class as a
     * parent.
     * <p>
     * Note this can be combined with annotationClass.
     *
     * @param superClass parent class
     */
    public void setMarkerInterface(Class<?> superClass) {
        this.markerInterface = superClass;
    }

    /**
     * Specifies which {@code SqlSessionTemplate} to use in the case that there is more than one in
     * the spring context.
     * Usually this is only needed when you have more than one datasource.
     * <p>
     * Note bean names are used, not bean references. This is because the scanner loads early during
     * the start process and
     * it is too early to build mybatis object instances.
     *
     * @param sqlSessionTemplateName Bean name of the {@code SqlSessionTemplate}
     * @since 1.1.0
     */
    public void setSqlSessionTemplateBeanName(String sqlSessionTemplateName) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateName;
    }

    /**
     * Specifies which {@code SqlSessionFactory} to use in the case that there is more than one in
     * the spring context.
     * Usually this is only needed when you have more than one datasource.
     * <p>
     * Note bean names are used, not bean references. This is because the scanner loads early during
     * the start process and
     * it is too early to build mybatis object instances.
     *
     * @param sqlSessionFactoryName Bean name of the {@code SqlSessionFactory}
     * @since 1.1.0
     */
    public void setSqlSessionFactoryBeanName(String sqlSessionFactoryName) {
        this.sqlSessionFactoryBeanName = sqlSessionFactoryName;
    }

    /**
     * Specifies a flag that whether execute a property placeholder processing or not.
     * <p>
     * The default is {@literal false}. This means that a property placeholder processing does not
     * execute.
     *
     * @param processPropertyPlaceHolders a flag that whether execute a property placeholder
     *                                    processing or not
     * @since 1.1.1
     */
    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    /**
     * The class of the {@link MapperFactoryBean} to return a mybatis proxy as spring bean.
     *
     * @param mapperFactoryBeanClass The class of the MapperFactoryBean
     * @since 2.0.1
     */
    public void setMapperFactoryBeanClass(Class<? extends MapperFactoryBean> mapperFactoryBeanClass) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * Gets beanNameGenerator to be used while running the scanner.
     *
     * @return the beanNameGenerator BeanNameGenerator that has been configured
     * @since 1.2.0
     */
    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    /**
     * Sets beanNameGenerator to be used while running the scanner.
     *
     * @param nameGenerator the beanNameGenerator to set
     * @since 1.2.0
     */
    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // left intentionally blank
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.2
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
        Map<String, Object> map = (Map<String, Object>) yamlMapFactoryBean.getObject().get("datasource");
        mappersPath = (String) map.get("mappersPath");
        primary = (String) map.get("primary");
        List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("dbs");
        if (items == null || items.size() == 0) {
            return;
        }
        for (Map<String, Object> item : items) {
            DbProperties dbProperties = new DbProperties();
            String key = (String) item.get("key");
            if (key.startsWith(DataSourcePrefix.master.name())) {
                masters.add(dbProperties);
            } else if (key.startsWith(DataSourcePrefix.slave.name())) {
                slaves.add(dbProperties);
            } else {
                throw new RuntimeException("illegal datasource key:" + key);
            }
            dbProperties.setKey(key);
            dbProperties.setHost((String) item.get("host"));
            dbProperties.setPort((Integer) item.get("port"));
            dbProperties.setDb((String) item.get("db"));
            dbProperties.setUsername((String) item.get("username"));
            dbProperties.setPassword((String) item.get("password"));
            dbProperties.setMinIdle((Integer) item.get("minIdle"));
            dbProperties.setMaxIdle((Integer) item.get("maxIdle"));
            dbProperties.setConnectionTimeout((Integer) item.get("connectionTimeout"));
        }
        log.info("init MapperScannerConfigurer, mappersPath={}", mappersPath);
        setBasePackage(mappersPath);

        for (DbProperties db : masters) {
            doPostProcessBeanDefinitionRegistry(db.getKey(), registry);
        }

        if (slaves != null && slaves.size() > 0) {
            for (DbProperties db : slaves) {
                doPostProcessBeanDefinitionRegistry(db.getKey(), registry);
            }
        }
    }

    private void doPostProcessBeanDefinitionRegistry(String key, BeanDefinitionRegistry registry) {
        if (this.processPropertyPlaceHolders) {
            processPropertyPlaceHolders();
        }

        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.setSqlSessionFactoryBeanName(String.format(MybatisXConstant.sqlSessionFactoryBeanName, key));
        scanner.setSqlSessionTemplateBeanName(String.format(MybatisXConstant.sqlSessionTemplateBeanName, key));
        scanner.setBeanNameGenerator(new MapperBeanNameGenerator(key));

        scanner.setAddToConfig(this.addToConfig);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setMarkerInterface(this.markerInterface);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setMapperFactoryBeanClass(this.mapperFactoryBeanClass);
        if (StringUtils.hasText(lazyInitialization)) {
            scanner.setLazyInitialization(Boolean.valueOf(lazyInitialization));
        }
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    /*
     * BeanDefinitionRegistries are called early in application startup, before BeanFactoryPostProcessors. This means that
     * PropertyResourceConfigurers will not have been loaded and any property substitution of this class' properties will
     * fail. To avoid this, find any PropertyResourceConfigurers defined in the context and run them on this class' bean
     * definition. Then update the values.
     */
    private void processPropertyPlaceHolders() {
        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class);

        if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
            BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
                                                                                                    .getBeanDefinition(beanName);

            // PropertyResourceConfigurer does not expose any methods to explicitly perform
            // property placeholder substitution. Instead, create a BeanFactory that just
            // contains this mapper scanner and post process the factory.
            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(beanName, mapperScannerBean);

            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }

            PropertyValues values = mapperScannerBean.getPropertyValues();

            this.basePackage = updatePropertyValue("basePackage", values);
            this.sqlSessionFactoryBeanName = updatePropertyValue("sqlSessionFactoryBeanName", values);
            this.sqlSessionTemplateBeanName = updatePropertyValue("sqlSessionTemplateBeanName", values);
            this.lazyInitialization = updatePropertyValue("lazyInitialization", values);
        }
        this.basePackage = Optional.ofNullable(this.basePackage).map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.sqlSessionFactoryBeanName = Optional.ofNullable(this.sqlSessionFactoryBeanName)
                                                 .map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.sqlSessionTemplateBeanName = Optional.ofNullable(this.sqlSessionTemplateBeanName)
                                                  .map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.lazyInitialization = Optional.ofNullable(this.lazyInitialization).map(getEnvironment()::resolvePlaceholders)
                                          .orElse(null);
    }

    private Environment getEnvironment() {
        return this.applicationContext.getEnvironment();
    }

    private String updatePropertyValue(String propertyName, PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);

        if (property == null) {
            return null;
        }

        Object value = property.getValue();

        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            return null;
        }
    }

}
