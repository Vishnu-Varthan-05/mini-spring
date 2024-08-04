package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MiniApplication {
    private Map<Class<?>, Object> container = new HashMap<>();

    public void start(String basePackage){

    }
    private Set<Class<?>> scanForComponents(String basePackage){
        return Set.of();
    }

    private Object createInstance(Class<?> clazz){
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        }catch (Exception e){
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    private void injectDependencies(Object bean){
        Class<?> clazz = bean.getClass();
        for (Field field: clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = container.get(fieldType);
                if (dependency == null) {
                    throw new RuntimeException("No bean found for " + fieldType.getName());
                }
                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency", e);
                }
            }
        }
    }
    public <T> T getBean(Class<T> clazz){
        return clazz.cast(container.get(clazz));
    }
    public void start(Class<?>... componentClasses){
        for (Class<?> clazz : componentClasses){
            if (clazz.isAnnotationPresent(Component.class)){
                Object instance = createInstance(clazz);
                container.put(clazz, instance);
            }
        }

        for (Object bean: container.values()){
            injectDependencies(bean);
        }
    }
}
