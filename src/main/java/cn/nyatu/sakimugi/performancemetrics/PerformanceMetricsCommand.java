package cn.nyatu.sakimugi.performancemetrics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class PerformanceMetricsCommand implements CommandExecutor {

    private final PerformanceMetricsPlugin plugin;

    public PerformanceMetricsCommand(PerformanceMetricsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage("§c用法: /mspt [10s|1m]");
            return true;
        }

        if (args.length == 0) {
            double tenSeconds = plugin.getAverageTickMs(10);
            double oneMinute = plugin.getAverageTickMs(60);
            sender.sendMessage("§bMSPT §7- 10s: §a" + format(tenSeconds) + "ms §7| 1m: §a" + format(oneMinute) + "ms");
            return true;
        }

        String input = args[0].trim().toLowerCase(Locale.ROOT);
        switch (input) {
            case "10s", "10" -> {
                sender.sendMessage("§bMSPT §7(10s): §a" + format(plugin.getAverageTickMs(10)) + "ms");
                return true;
            }
            case "1m", "60s", "60" -> {
                sender.sendMessage("§bMSPT §7(1m): §a" + format(plugin.getAverageTickMs(60)) + "ms");
                return true;
            }
            default -> {
                sender.sendMessage("§c用法: /mspt [10s|1m]");
                return true;
            }
        }
    }

    private String format(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}