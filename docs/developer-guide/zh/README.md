# Microsphere Nacos — 开发者文档

面向贡献者的指南：每个模块的架构、源码级分析、代码实际使用的设计模式、每个复杂流程的 Mermaid 图、扩展点，
以及直接依据仓库文件读出的构建/测试流程。

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

| 模块 | 指南文档 |
|------|----------|
| `microsphere-nacos-openapi` | [microsphere-nacos-openapi](../current/microsphere-nacos-openapi.md) |
| `microsphere-nacos-discovery-spring-cloud` | [microsphere-nacos-discovery-spring-cloud](../current/microsphere-nacos-discovery-spring-cloud.md) |
| `microsphere-nacos-parent` | [microsphere-nacos-parent](../current/microsphere-nacos-parent.md) |
| `microsphere-nacos-dependencies` | [microsphere-nacos-dependencies](../current/microsphere-nacos-dependencies.md) |

## 一篇开发者文档包含什么

- 每线的索引（`README.md`）承载整个项目的架构、模块/继承流程图，以及构建与测试流程。
- 分层架构：传输层 → 版本门面（V1/V2）→ 领域客户端 → 模型与（反）序列化。
- 按包的源码分析，含真实类名、文件路径与简短逐字代码片段。
- 只在代码确有体现之处断言设计模式，并给出指向位置。
- 针对鉴权、配置拉取与监听、实例/服务注册（V1 对比 V2）、Spring Cloud 发现调用链的 Mermaid 流程图与时序图。
- 扩展点、错误处理模型，以及观察到的行为怪癖。

## English

[English](../README.md)
