# Microsphere Nacos — 用户文档

面向消费者的 `microsphere-nacos` 指南：每个模块提供什么、Maven 坐标、公共 API、取自项目自身测试的可用代码示例，
以及版本与兼容范围。

英文为默认语言；每条版本线在 `zh/` 下都有中文镜像。

- [English](../README.md)
- [文档主页](../../README.md)
- [仓库 README](../../../README.md)

## 选择版本线

| 版本线 | 源分支 | `${revision}` | 指南 |
|--------|--------|---------------|------|
| `current` | `release` | `0.2.0-SNAPSHOT` | [current](../current/README.md) |
| `1.x` | `release-1.x` | `0.2.0-SNAPSHOT` | [1.x](../1.x/README.md) |

## 模块

无论选择哪条版本线，指南都覆盖同样的四个 Maven 模块：

| 模块 | 指南文档 |
|------|----------|
| `microsphere-nacos-openapi` | [microsphere-nacos-openapi](../current/microsphere-nacos-openapi.md) |
| `microsphere-nacos-discovery-spring-cloud` | [microsphere-nacos-discovery-spring-cloud](../current/microsphere-nacos-discovery-spring-cloud.md) |
| `microsphere-nacos-parent` | [microsphere-nacos-parent](../current/microsphere-nacos-parent.md) |
| `microsphere-nacos-dependencies` | [microsphere-nacos-dependencies](../current/microsphere-nacos-dependencies.md) |

若阅读 `1.x` 线，则相同文件名位于 [1.x](../1.x/) 下。

## 一篇用户文档包含什么

- 概述：模块的用途以及何时选择它。
- Maven 坐标与 BOM 导入片段。
- 基于源码中真实的类名、方法名与属性名的 API 参考。
- 改编自 `microsphere-nacos-openapi/src/test` 的代码示例，每个示例注明其源测试。
- 该版本线的版本信息与兼容范围。
- 模块依赖关系的 Mermaid 流程图。

## English

[English](../README.md)
