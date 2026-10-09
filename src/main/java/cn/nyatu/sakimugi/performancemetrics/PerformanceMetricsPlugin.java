package cn.nyatu.sakimugi.performancemetrics;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PerformanceMetricsPlugin extends JavaPlugin implements Listener {

    private static final int TPS_WINDOW_CAPACITY = 4096;
    private static final long TPS_WINDOW_NANOS = 10_000_000_000L;

    private static PerformanceMetricsPlugin instance;
    private final long[] tickTimes = new long[TPS_WINDOW_CAPACITY];
    private final long[] tickCounts = new long[TPS_WINDOW_CAPACITY];
    private int windowHead;
    private int windowSize;
    private long tickCount;
    private volatile double currentMspt;
    private volatile double currentTps;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("gpm"), "Command /gpm is not registered in plugin.yml")
            .setExecutor(new PerformanceMetricsCommand(this));
        Bukkit.getPluginManager().registerEvents(this, this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            boolean registered = new PerformanceMetricsExpansion().register();
            if (registered) {
                getLogger().info("PlaceholderAPI placeholders registered: %gpm_mspt%, %gpm_tps%.");
            } else {
                getLogger().severe("Could not register PlaceholderAPI expansion 'gpm'. Check for another expansion using the same identifier.");
            }
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerTickEnd(ServerTickEndEvent event) {
        currentMspt = Math.max(0.0D, event.getTickDuration());

        long now = System.nanoTime();
        long currentTick = ++tickCount;
        if (windowSize == TPS_WINDOW_CAPACITY) {
            windowHead = (windowHead + 1) % TPS_WINDOW_CAPACITY;
            windowSize--;
        }
        int writeIndex = (windowHead + windowSize) % TPS_WINDOW_CAPACITY;
        tickTimes[writeIndex] = now;
        tickCounts[writeIndex] = currentTick;
        windowSize++;

        long cutoff = now - TPS_WINDOW_NANOS;
        while (windowSize > 1 && tickTimes[(windowHead + 1) % TPS_WINDOW_CAPACITY] <= cutoff) {
            windowHead = (windowHead + 1) % TPS_WINDOW_CAPACITY;
            windowSize--;
        }

        if (windowSize > 1) {
            int oldestIndex = windowHead;
            long elapsedNanos = now - tickTimes[oldestIndex];
            long elapsedTicks = currentTick - tickCounts[oldestIndex];
            if (elapsedNanos > 0L) {
                currentTps = elapsedTicks * 1_000_000_000.0D / elapsedNanos;
            }
        }
    }

    public static PerformanceMetricsPlugin getInstance() {
        return instance;
    }

    public double getMspt() {
        return currentMspt;
    }

    public double getTps() {
        return currentTps;
    }

    public String getColoredMspt() {
        return formatColoredMspt(getMspt());
    }

    static String formatColoredMspt(double mspt) {
        String color = mspt > 50.0D ? "&c" : mspt > 40.0D ? "&e" : "&a";
        return color + String.format(java.util.Locale.ROOT, "%.2f", mspt);
    }

    public String getColoredTps() {
        return formatColoredTps(getTps());
    }

    static String formatColoredTps(double tps) {
        String color = tps < 16.0D ? "&c" : tps < 18.0D ? "&e" : "&a";
        return color + String.format(java.util.Locale.ROOT, "%.2f", tps);
    }
}
