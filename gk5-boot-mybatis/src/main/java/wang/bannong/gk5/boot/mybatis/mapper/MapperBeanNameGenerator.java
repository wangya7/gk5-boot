package wang.bannong.gk5.boot.mybatis.mapper;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.util.Objects;

public class MapperBeanNameGenerator implements BeanNameGenerator {

    private String prefix;

    /**
     * @param prefix
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
