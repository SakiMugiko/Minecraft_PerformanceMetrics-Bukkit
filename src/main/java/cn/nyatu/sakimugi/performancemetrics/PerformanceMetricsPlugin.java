package cn.nyatu.sakimugi.performancemetrics;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PerformanceMetricsPlugin extends JavaPlugin {

    private static PerformanceMetricsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        Objects.requireNonNull(getCommand("mspt"), "Command /mspt is not registered in plugin.yml").setExecutor(new PerformanceMetricsCommand(this));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PerformanceMetricsExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static PerformanceMetricsPlugin getInstance() {
        return instance;
    }

    public double getAverageTickMs(int seconds) {
        Server server = Bukkit.getServer();
        double average = server.getAverageTickTime();

        if (seconds >= 60) {
            return average;
        }

        long[] tickTimes = server.getTickTimes();
        if (tickTimes == null || tickTimes.length == 0) {
            return average;
        }

        int sampleCount = Math.min(tickTimes.length, Math.max(1, seconds * 20));
        int startIndex = tickTimes.length - sampleCount;

        long total = 0L;
        for (int i = startIndex; i < tickTimes.length; i++) {
            total += Math.max(0L, tickTimes[i]);
        }

        return tickNanosToMillis(total / (double) sampleCount);
    }

    public static double tickNanosToMillis(double tickNanos) {
        return tickNanos / 1_000_000.0D;
    }
}