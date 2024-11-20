package wang.bannong.gk5.boot.sample.dubbo.serialization;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

public class SerializationOptimizerImpl implements SerializationOptimizer {
    @Override
    public Collection<Class<?>> getSerializableClasses() {
        List<Class<?>> classes = new LinkedList<>();
        classes.add(String.class);
        return classes;
    }
}
