package sparkWithSpring;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();

    private JavaConfig config = new JavaConfig();
    private List<ObjectConfigurer> objectConfigurers = new ArrayList<>();

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        objectConfigurers.add(new InjectRandomIntObjectConfigurer());
        objectConfigurers.add(new InjectObjectConfigurer());
    }




    public <T> T createObject(Class<T> type) throws IllegalAccessException, InstantiationException {
        T t = create(type);

        configure(t);


        return t;
    }

    private <T> T create(Class<T> type) throws InstantiationException, IllegalAccessException {
        if (type.isInterface()) {
          type =  config.getImpl(type);
        }
        return type.newInstance();
    }

    private <T> void configure(T t) {
        for (ObjectConfigurer objectConfigurer : objectConfigurers) {
            objectConfigurer.configure(t);
        }
    }


}
