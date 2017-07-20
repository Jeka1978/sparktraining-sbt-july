package taxi;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Data
@Builder
public class Trip {
    private int km;
    @NonNull
    private String id;
    private String cityName;
}
