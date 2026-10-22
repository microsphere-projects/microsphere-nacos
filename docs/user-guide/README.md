# Microsphere Nacos — User Guide

Consumer-facing guides for the `microsphere-nacos` artifacts: what each module offers, its Maven coordinates, its
public API, working code examples drawn from the project's own tests, and the version / compatibility range.

English is the default language; each version line has a Chinese mirror under `zh/`.

- [中文](zh/README.md)
- [Docs home](../README.md)
- [Repository README](../../README.md)

## Choose your version line

| Version line | Source branch | `${revision}` | Guide |
|--------------|---------------|---------------|-------|
| `current` | `release` | `0.2.0-SNAPSHOT` | [current](current/README.md) |
| `1.x` | `release-1.x` | `0.2.0-SNAPSHOT` | [1.x](1.x/README.md) |

## Modules

Whichever line you pick, the guide covers the same four Maven modules:

| Module | Guide document |
|--------|----------------|
| `microsphere-nacos-openapi` | [microsphere-nacos-openapi](current/microsphere-nacos-openapi.md) |
| `microsphere-nacos-discovery-spring-cloud` | [microsphere-nacos-discovery-spring-cloud](current/microsphere-nacos-discovery-spring-cloud.md) |
| `microsphere-nacos-parent` | [microsphere-nacos-parent](current/microsphere-nacos-parent.md) |
| `microsphere-nacos-dependencies` | [microsphere-nacos-dependencies](current/microsphere-nacos-dependencies.md) |

Reading the `1.x` line instead, the same filenames live under [1.x](1.x/).

## What is in a user guide document

- Overview: what the module is for and when to choose it.
- Maven coordinates and BOM import snippet.
- API reference with real class, method, and property names read from the source.
- Code examples adapted from `microsphere-nacos-openapi/src/test`, each citing its source test.
- Version information and the compatibility range for the line.
- A Mermaid flowchart of the module's dependencies.

## 中文

[中文](zh/README.md)
