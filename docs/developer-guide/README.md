# Microsphere Nacos — Developer Guide

Contributor-facing guides: the architecture of each module, source-level analysis, the design patterns the code
actually uses, Mermaid diagrams for every complex flow, extension points, and the build/test workflow read straight
from the repository's files.

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

| Module | Guide document |
|--------|----------------|
| `microsphere-nacos-openapi` | [microsphere-nacos-openapi](current/microsphere-nacos-openapi.md) |
| `microsphere-nacos-discovery-spring-cloud` | [microsphere-nacos-discovery-spring-cloud](current/microsphere-nacos-discovery-spring-cloud.md) |
| `microsphere-nacos-parent` | [microsphere-nacos-parent](current/microsphere-nacos-parent.md) |
| `microsphere-nacos-dependencies` | [microsphere-nacos-dependencies](current/microsphere-nacos-dependencies.md) |

## What is in a developer guide document

- The per-line index (`README.md`) carries the whole-project architecture, the module/inheritance flowchart, and the
  build-and-test workflow.
- Layered architecture: transport → versioned facade (V1/V2) → domain clients → models and (de)serialization.
- Source analysis by package with real class names, file paths, and short verbatim snippets.
- Design patterns asserted only where the code shows them, with pointers.
- Mermaid flow and sequence diagrams for authentication, config fetch and listening, instance/service registration
  (V1 vs V2), and the Spring Cloud discovery call chain.
- Extension points, the error-handling model, and observed behavioural quirks.

## 中文

[中文](zh/README.md)
