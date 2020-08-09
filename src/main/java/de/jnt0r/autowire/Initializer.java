package de.jnt0r.autowire;

import de.jnt0r.autowire.annotations.Autowire;
import de.jnt0r.autowire.annotations.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Initializer {

    private static final Logger logger = LogManager.getLogger(Initializer.class.getName());

    private static final Map<Class<?>, Object> components = new HashMap<>();

    public static <T> T initialize(Class<T> clazz, String[] args) {
        try {
            Set<Class<?>> componentClasses = componentScan(clazz.getPackage());
            initializeComponents(componentClasses);
            logger.info("Initialized components:");
            components.forEach((k, v) -> {
                logger.info("\t" + k + ": " + v);
            });
            injectComponents();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        }


        try {
            T applicationInstance = clazz.newInstance();
            injectComponentsInto(clazz, applicationInstance);
            return applicationInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.exit(2);
        }

        return null;
    }

    private static void injectComponents() throws IllegalAccessException {
        for (Map.Entry<Class<?>, Object> entry : components.entrySet()) {
            Class<?> key = entry.getKey();
            Object value = entry.getValue();
            injectComponentsInto(key, value);
        }
    }

    private static void injectComponentsInto(Class<?> clazz, Object component) throws IllegalAccessException {
        logger.info("Injecting into component " + clazz + " " + component);
        for (Field field : clazz.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Autowire.class);
            if (annotation != null) {
                field.setAccessible(true);
                Object value = components.get(field.getType());
                logger.info(value);
                field.set(component, value);
            }
        }
    }

    private static void initializeComponents(Set<Class<?>> componentClasses) throws IllegalAccessException, InstantiationException {
        for (Class<?> component : componentClasses) {
            initializeComponent(component);
        }
    }

    private static Set<Class<?>> componentScan(Package basePackage) {
        logger.info("Scanning for components...");

        Set<Class<?>> componentClasses = new HashSet<>();
        Reflections ref = new Reflections(basePackage);
        for (Class<?> cl : ref.getTypesAnnotatedWith(Component.class)) {
            Component findable = cl.getAnnotation(Component.class);
            logger.info("Found component with name '{}' in class {}",
                    findable.value(), cl.getCanonicalName());
            componentClasses.add(cl);
        }
        logger.info("Finished component scan");

        return componentClasses;
    }

    private static <T> T getT(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        Reflections ref = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(clazz))
                .setScanners(new FieldAnnotationsScanner()));
        T applicationInstance = clazz.newInstance();

        Set<Field> fields = ref.getFieldsAnnotatedWith(Autowire.class);
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = components.get(field.getType());
            field.set(applicationInstance, value);
            logger.info("Set field " + field.getName() + " of class " + clazz.getCanonicalName() + " to " + value);
        }

        return applicationInstance;
    }

    private static void initializeComponent(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        if (!components.containsKey(clazz)) {
            Object value = clazz.newInstance();
            components.put(clazz, value);
            logger.info("Initialized component " + clazz.getCanonicalName());
        } else {
            logger.warn("Component " + clazz.getCanonicalName() + " already initialized!");
        }
    }
    
}
