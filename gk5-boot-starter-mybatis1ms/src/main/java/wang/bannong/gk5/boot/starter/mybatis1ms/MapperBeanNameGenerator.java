package wang.bannong.gk5.boot.starter.mybatis1ms;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.util.Objects;

/**
 * Mybatis Mapper对象名称生成器
 * @author wang.bannong(inc11003307@gmail.com)
 */
public class MapperBeanNameGenerator implements BeanNameGenerator {

    private String prefix;

    /**
     * @param prefix 前缀
     */
    public MapperBeanNameGenerator(String prefix) {
        Objects.requireNonNull(prefix, "prefix cannot be null");
        this.prefix = prefix;
    }

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return prefix + ClassUtils.getShortName(definition.getBeanClassName());
    }
}
