package com.polytechcloud.polytechcloud.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.MeterRegistry;

public class CustomMetricsConfig {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            registry.config().commonTags("application", "metrics_service");
        };
    }

}