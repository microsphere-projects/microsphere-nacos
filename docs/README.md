# Microsphere Nacos documentation

Guides are produced from the two maintained release branches only — never from `main` or `dev` — and are organised by
version line, then by guide type, then by Maven module. English is the default language; Chinese mirrors live beside
each tree under a `zh/` directory with identical file names.

- [中文文档首页](zh/README.md)

## Version lines

| Directory | Source branch | `${revision}` | External Microsphere Spring Cloud |
|-----------|---------------|---------------|-----------------------------------|
| [`current`](user-guide/current/README.md) | `release` | `0.2.0-SNAPSHOT` | `0.2.26` |
| [`1.x`](user-guide/1.x/README.md) | `release-1.x` | `0.2.0-SNAPSHOT` | `0.2.25` |

## Guide types

| | User Guide | Developer Guide |
|------|------------|-----------------|
| Audience | Application developers consuming the artifacts | Contributors and maintainers |
| Contents | Maven coordinates, API reference, code examples from the project's own tests, version and compatibility range | Architecture, source analysis, design patterns, flow diagrams, extension points |
| `current` (EN) | [user-guide/current](user-guide/current/README.md) | [developer-guide/current](developer-guide/current/README.md) |
| `current` (中文) | [user-guide/zh/current](user-guide/zh/current/README.md) | [developer-guide/zh/current](developer-guide/zh/current/README.md) |
| `1.x` (EN) | [user-guide/1.x](user-guide/1.x/README.md) | [developer-guide/1.x](developer-guide/1.x/README.md) |
| `1.x` (中文) | [user-guide/zh/1.x](user-guide/zh/1.x/README.md) | [developer-guide/zh/1.x](developer-guide/zh/1.x/README.md) |

## Modules documented

One document per Maven module of the reactor, in each guide of each version line:

| Module | Packaging | Purpose |
|--------|-----------|---------|
| `microsphere-nacos-openapi` | jar | Typed Nacos Open API client for the V1 and V2 HTTP APIs (config, discovery, namespace, server, Raft, client introspection) |
| `microsphere-nacos-discovery-spring-cloud` | jar | Spring Cloud `DiscoveryClient` implementation and Boot auto-configuration on top of the Open API client |
| `microsphere-nacos-parent` | pom | Internal parent: pins `httpclient` and imports the Microsphere Spring Cloud BOM |
| `microsphere-nacos-dependencies` | pom | Consumer-facing BOM managing the two jar modules |

## Version and compatibility facts

- GroupId `io.github.microsphere-projects`; the reactor version comes from `${revision}`.
- CI builds test JDK **17, 21 and 25** against the `spring-cloud-2022` … `spring-cloud-2025` profiles
  (`.github/workflows/maven-build.yml`); integration tests run against a docker-compose-provided Nacos server.
- Spring Boot, Spring Cloud and the compile JDK baseline are **inherited from the external
  `microsphere-spring-cloud-parent` / `microsphere-spring-cloud-dependencies` POMs**, not declared in this repository,
  so no fixed numbers for them are asserted anywhere in these guides.
- The repository has published no git tags; the branch table in the repository README quotes released versions that no
  POM in these snapshots declares, and those claims are labelled as such wherever they appear.

## Provenance

Every guide states the exact branch and revision it was generated from at the top of its index. Snapshots were taken as
detached worktrees of `origin/release` and `origin/release-1.x`; nothing in these docs reflects uncommitted work.

[Repository README](../README.md)

## 中文

[中文](zh/README.md)
