package com.xiatian.authserver.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "xiatian")
@Data
@Component
public class TestConfig {
    private String x;
}
