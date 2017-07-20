package transactions;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evegeny on 20/07/2017.
 */
@Component
public class CountryCodeToCountryName implements UDF1<String,String>, Serializable {


    @Autowired
    private transient SQLContext sqlContext;

    private Map<String, String> map = new HashMap<>();



    @Value("${countries}")
    private void initMap(String[] countries) {
        for (String country : countries) {
            String[] strings = country.split(":");
            map.put(strings[0], strings[1]);
        }
        registerUdf();
    }

    private void registerUdf() {
        sqlContext.udf().register(CountryCodeToCountryName.class.getName(), this, DataTypes.StringType);
    }


    @Override
    public String call(String countryCode) throws Exception {
        return map.get(countryCode);
    }
}
