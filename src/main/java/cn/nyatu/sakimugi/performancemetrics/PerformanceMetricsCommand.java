package cn.nyatu.sakimugi.performancemetrics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class PerformanceMetricsCommand implements CommandExecutor {

    private final PerformanceMetricsPlugin plugin;

    public PerformanceMetricsCommand(PerformanceMetricsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage("§c用法: /gpm");
            return true;
        }
        sender.sendMessage("§bMSPT §7| " + colorize(plugin.getColoredMspt()) + "ms §7| §bTPS §7| " + colorize(plugin.getColoredTps()));
        return true;
    }

    private String colorize(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
