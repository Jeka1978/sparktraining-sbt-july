package sparkWithSpring;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evegeny on 20/07/2017.
 */
public class JavaConfig {
    private Map<Class, Class> map = new HashMap<>();

    public JavaConfig() {
        map.put(Speaker.class, PopupSpeaker.class);
        map.put(Cleaner.class, CleanerImpl.class);
    }


    public <T> Class<T> getImpl(Class<T> type) {
        return map.get(type);
    }
}
