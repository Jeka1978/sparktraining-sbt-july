package sparkWithSpring;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class InjectObjectConfigurer implements ObjectConfigurer {
    @Override
    @SneakyThrows
    public void configure(Object o) {
        Class<?> type = o.getClass();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                Class<?> fieldType = field.getType();
                Object object = ObjectFactory.getInstance().createObject(fieldType);
                field.setAccessible(true);
                field.set(o,object);
            }
        }
    }
}
