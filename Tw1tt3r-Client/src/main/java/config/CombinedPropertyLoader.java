package config;

import lombok.SneakyThrows;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

public class CombinedPropertyLoader {
    private static CombinedPropertyLoader instance;
    private CombinedConfiguration configuration;

    @SneakyThrows
    private CombinedPropertyLoader() {
        Parameters params = new Parameters();
        CombinedConfigurationBuilder builder = new CombinedConfigurationBuilder()
                .configure(params.fileBased().setFileName("configuration.xml"));
        configuration = builder.getConfiguration();
    }

    @SneakyThrows
    public static synchronized CombinedPropertyLoader getInstance() {
        if (instance == null) {
            instance = new CombinedPropertyLoader();
        }
        return(instance);
    }

    public String getProperty(String key) {
        return((String) configuration.getProperty(key));
    }
}
