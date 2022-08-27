package com.maple.harbor;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.maple.harbor.dao")
public class HarborBootstrap implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HarborBootstrap.class, args);
    }

    @Autowired
    private ConfigurableEnvironment env;

    @Override
    public void run(String... args) throws Exception {
        MutablePropertySources propertySources = env.getPropertySources();
        List<OriginTrackedMapPropertySource> classpathConfigs = propertySources.stream()
                                                                        .filter(p -> p instanceof OriginTrackedMapPropertySource)
                                                                        .filter(p -> p.getName().contains("class path resource")
                                                                                             || p.getName().contains("Config resource"))
                                                                        .map(p -> (OriginTrackedMapPropertySource) p)
                                                                        .collect(Collectors.toList());
        log.info("start to print application config: ");
        classpathConfigs.forEach(r -> {
            Map<String, Object> source = r.getSource();
            source.forEach((k, v) -> log.info("-> " + k + " = " + v));
        });
    }

}
