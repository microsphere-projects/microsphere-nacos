# Microsphere Nacos 文档

本指南仅基于两条受维护的 release 分支生成——绝不来自 `main` 或 `dev`——并按版本线、指南类型、Maven 模块组织。
英文为默认语言；中文镜像以相同文件名存放在每棵树的 `zh/` 目录下。

- [English documentation home](../README.md)

## 版本线

| 目录 | 源分支 | `${revision}` | 外部 Microsphere Spring Cloud |
|------|--------|---------------|-------------------------------|
| [`current`](../user-guide/zh/current/README.md) | `release` | `0.2.0-SNAPSHOT` | `0.2.26` |
| [`1.x`](../user-guide/zh/1.x/README.md) | `release-1.x` | `0.2.0-SNAPSHOT` | `0.2.25` |

## 指南类型

| | 用户文档 | 开发者文档 |
|------|----------|------------|
| 读者 | 消费这些构件的应用开发者 | 贡献者与维护者 |
| 内容 | Maven 坐标、API 参考、来自项目自身测试的代码示例、版本与兼容范围 | 架构、源码分析、设计模式、流程图、扩展点 |
| `current`（EN） | [user-guide/current](../user-guide/current/README.md) | [developer-guide/current](../developer-guide/current/README.md) |
| `current`（中文） | [user-guide/zh/current](../user-guide/zh/current/README.md) | [developer-guide/zh/current](../developer-guide/zh/current/README.md) |
| `1.x`（EN） | [user-guide/1.x](../user-guide/1.x/README.md) | [developer-guide/1.x](../developer-guide/1.x/README.md) |
| `1.x`（中文） | [user-guide/zh/1.x](../user-guide/zh/1.x/README.md) | [developer-guide/zh/1.x](../user-guide/zh/1.x/README.md) |

## 被记录的模块

反应堆中每个 Maven 模块，在每条版本线的每种指南中各有一篇文档：

| 模块 | 打包 | 用途 |
|------|------|------|
| `microsphere-nacos-openapi` | jar | 面向 V1 与 V2 HTTP API 的强类型 Nacos Open API 客户端（配置、服务发现、命名空间、服务端、Raft、客户端自省） |
| `microsphere-nacos-discovery-spring-cloud` | jar | 在该 Open API 客户端之上的 Spring Cloud `DiscoveryClient` 实现与 Boot 自动配置 |
| `microsphere-nacos-parent` | pom | 内部父 POM：固定 `httpclient` 并导入 Microsphere Spring Cloud BOM |
| `microsphere-nacos-dependencies` | pom | 面向消费者的 BOM，管理上述两个 jar 模块 |

## 版本与兼容事实

- GroupId 为 `io.github.microsphere-projects`；反应堆版本来自 `${revision}`。
- CI 构建在 JDK **17、21 和 25** 上针对 `spring-cloud-2022` … `spring-cloud-2025` profile 进行测试
  （`.github/workflows/maven-build.yml`）；集成测试运行在由 docker-compose 提供的 Nacos 服务端上。
- Spring Boot、Spring Cloud 与编译 JDK 基线**继承自外部 `microsphere-spring-cloud-parent` /
  `microsphere-spring-cloud-dependencies` POM**，并非在本仓库中声明，因此本指南任何位置都不对它们断言固定数值。
- 本仓库尚未发布任何 git tag；仓库 README 的分支表中引用的已发布版本，在这些快照的任何 POM 中都没有声明，凡出现处均已如此标注。

## 来源说明

每篇指南都在其索引顶部标明生成时所用的确切分支与 revision。快照以 `origin/release` 与 `origin/release-1.x` 的
分离（detached）worktree 采集；本指南不反映任何未提交的工作。

[仓库 README](../../README.md)

## English

[English](../README.md)
