package org.example.bring.context;

import org.example.bring.annotation.Bean;
import org.example.bring.exception.NoSuchBeanException;
import org.example.bring.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {
    private static final ConcurrentHashMap<String, Object> context = new ConcurrentHashMap<>();
    public ApplicationContextImpl(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> typesToBeInstantiated = reflections.getTypesAnnotatedWith(Bean.class);
        createBeans(typesToBeInstantiated);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        Map<String, T> allBeans = getAllBeans(beanType);
        if (allBeans.size() > 1) {
            throw new NoUniqueBeanException(String.format("More than one implementation of %s found", beanType.getName()));
        }
        return allBeans.values().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchBeanException(String.format("Could not find any implementation of %s", beanType.getName())));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        Object obj = context.get(name);
        return beanType.cast(obj);
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return context.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, value -> beanType.cast(value.getValue())));
    }

    private void createBeans(Set<Class<?>> typesToBeInstantiated) {
        typesToBeInstantiated.forEach(clazz -> {
            try {
                Bean bean = clazz.getAnnotation(Bean.class);
                String beanName = bean.value().length() != 0
                        ? bean.value()
                        : uncapitalize(clazz.getSimpleName());
                Object instance = clazz.getConstructor().newInstance();
                context.put(beanName, instance);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static String uncapitalize(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
