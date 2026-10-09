package cn.nyatu.sakimugi.performancemetrics;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * PlaceholderAPI expansion for the server's calculated average MSPT.
 */
public final class PerformanceMetricsExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mspt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SakiMugi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        PerformanceMetricsPlugin plugin = PerformanceMetricsPlugin.getInstance();
        if (plugin == null) {
            return "0.00";
        }

        String normalized = params == null ? "" : params.trim();
        if (isDefaultParam(normalized)) {
            return format(plugin.getAverageTickMs(60));
        }

        switch (normalized.toLowerCase(Locale.ROOT)) {
            case "10s" -> {
                return format(plugin.getAverageTickMs(10));
            }
            case "10", "10sec", "10seconds" -> {
                return format(plugin.getAverageTickMs(10));
            }
            case "1m", "60s", "60", "1min", "1minute" -> {
                return format(plugin.getAverageTickMs(60));
            }
            default -> {
                return format(plugin.getAverageTickMs(60));
            }
        }
    }

    private boolean isDefaultParam(String params) {
        if (params == null || params.isBlank()) {
            return true;
        }

        String normalized = params.trim();
        return normalized.equals("_")
            || normalized.equalsIgnoreCase("default")
            || normalized.equalsIgnoreCase("null")
            || normalized.equalsIgnoreCase("none");
    }

    private String format(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}