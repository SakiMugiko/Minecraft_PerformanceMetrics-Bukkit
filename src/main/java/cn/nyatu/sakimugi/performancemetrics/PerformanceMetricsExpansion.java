package cn.nyatu.sakimugi.performancemetrics;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/** PlaceholderAPI expansion for the server's MSPT and TPS. */
public final class PerformanceMetricsExpansion extends PlaceholderExpansion {

    /** Keep this plugin's internal expansion registered across /papi reload. */
    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "gpm";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SakiMugi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        PerformanceMetricsPlugin plugin = PerformanceMetricsPlugin.getInstance();
        String normalized = params == null ? "" : params.trim().toLowerCase(Locale.ROOT);
        if (plugin == null) {
            return switch (normalized) {
                case "mspt", "tps" -> "0.00";
                default -> null;
            };
        }

        return switch (normalized) {
            case "mspt" -> format(plugin.getMspt());
            case "tps" -> format(plugin.getTps());
            case "mspt_c" -> plugin.getColoredMspt();
            case "tps_c" -> plugin.getColoredTps();
            default -> null;
        };
    }

    private String format(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}
