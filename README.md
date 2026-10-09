# PerformanceMetrics

一个极简的 PlaceholderAPI Expansion，只提供服务器平均 MSPT。

## Placeholder

```text
%mspt%
```

返回示例：

```text
12.37
```

因此可以直接写成：

```text
&f⚡ MSPT: &a%mspt%ms
```

## 运行环境

- Paper / Purpur 1.21.1+
- Java 21
- PlaceholderAPI

## 原理

直接使用 Paper API 的：

```java
Bukkit.getServer().getAverageTickTime()
```

该 API 返回服务器平均 tick 时间，单位为毫秒，因此这里不自己维护采样数组，也不会产生额外定时任务。

## 安装

将编译后的：

```text
PerformanceMetrics-1.0.0.jar
```

放入：

```text
/plugins/PlaceholderAPI/expansions/
```

然后执行：

```text
/papi reload
```

测试：

```text
/papi parse me %mspt%
```

应该得到类似：

```text
12.37
```
