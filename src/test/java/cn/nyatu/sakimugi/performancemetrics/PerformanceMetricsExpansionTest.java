package cn.nyatu.sakimugi.performancemetrics;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceMetricsExpansionTest {

    @Test
    void identifierShouldBeMspt() {
        PerformanceMetricsExpansion expansion = new PerformanceMetricsExpansion();
        assertTrue("mspt".equals(expansion.getIdentifier()));
    }

    @Test
    void tickTimesShouldBeConvertedFromNanosecondsToMilliseconds() {
        assertTrue(Math.abs(PerformanceMetricsPlugin.tickNanosToMillis(34_000_000.0D) - 34.0D) < 0.0001D);
        assertTrue(Math.abs(PerformanceMetricsPlugin.tickNanosToMillis(1_500_000.0D) - 1.5D) < 0.0001D);
    }

    @Test
    void defaultPlaceholderParametersShouldStillResolve() {
        PerformanceMetricsExpansion expansion = new PerformanceMetricsExpansion();
        assertTrue(expansion.onRequest(null, "") != null);
        assertTrue(expansion.onRequest(null, "_") != null);
        assertTrue(expansion.onRequest(null, "default") != null);
    }

    @Test
    void placeholderApiMetadataShouldBePresent() throws Exception {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("expansion.yml")) {
            assertNotNull(input, "expansion.yml should exist in the jar resources");
            String yaml = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(yaml.contains("name: PerformanceMetrics"));
            assertTrue(yaml.contains("main: cn.nyatu.sakimugi.performancemetrics.PerformanceMetricsExpansion"));
        }
    }
}