# PerformanceMetrics

轻量级 Paper 插件，用于查看服务器 MSPT 和最近 10 秒平均 TPS

## 命令

```text
/gpm
```

显示最近一次 tick 的 MSPT 和最近 10 秒平均 TPS。数值会按负载着色：

- MSPT：大于 50 为红色，大于 40 为黄色，其余为绿色。
- TPS：低于 16 为红色，低于 18 为黄色，其余为绿色。

权限：`gpm.use`，默认所有玩家可用。

## PlaceholderAPI 变量

| 变量 | 返回值 |
| --- | --- |
| `%gpm_mspt%` | MSPT 数值，例如 `12.34` |
| `%gpm_tps%` | 最近 10 秒平均 TPS，例如 `20.00` |
| `%gpm_mspt_c%` | 带颜色代码的 MSPT，例如 `&a12.34` |
| `%gpm_tps_c%` | 带颜色代码的 TPS，例如 `&a20.00` |

着色阈值与 `/gpm` 命令一致。安装 PlaceholderAPI 后，变量会由插件自动注册。

## 运行环境

- Paper / Purpur 1.21.1+
- Java 21+
- PlaceholderAPI（可选）

## 安装

将 `PerformanceMetrics-1.1.0.jar` 放入服务器的 `plugins` 目录，然后重启服务器。
