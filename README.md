# Microsphere Nacos

> Microsphere Projects for Nacos

[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/microsphere-projects/microsphere-nacos)
[![Maven Build](https://github.com/microsphere-projects/microsphere-nacos/actions/workflows/maven-build.yml/badge.svg)](https://github.com/microsphere-projects/microsphere-nacos/actions/workflows/maven-build.yml)
[![Codecov](https://codecov.io/gh/microsphere-projects/microsphere-nacos/branch/main/graph/badge.svg)](https://app.codecov.io/gh/microsphere-projects/microsphere-nacos)
![Maven](https://img.shields.io/maven-central/v/io.github.microsphere-projects/microsphere-nacos.svg)
![License](https://img.shields.io/github/license/microsphere-projects/microsphere-nacos.svg)

Microsphere Nacos

## Modules

TODO

## Getting Started

The easiest way to get started is by adding the Microsphere Nacos BOM (Bill of Materials) to your project's
pom.xml:

```xml

<dependencyManagement>
    <dependencies>
        ...
        <!-- Microsphere Nacos Dependencies -->
        <dependency>
            <groupId>io.github.microsphere-projects</groupId>
            <artifactId>microsphere-nacos-dependencies</artifactId>
            <version>${microsphere-nacos.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        ...
    </dependencies>
</dependencyManagement>
```

`${microsphere-nacos.version}` has two branches:

| **Branches** | **Purpose**                                      | **Latest Version** |
|--------------|--------------------------------------------------|--------------------|
| **main**     | Compatible with Spring Cloud 2022.0.x - 2025.0.x | `0.2.0`            |
| **1.x**      | Compatible with Spring Cloud Hoxton - 2021.0.x   | `0.1.0`            |

## Building from Source

You don't need to build from source unless you want to try out the latest code or contribute to the project.

To build the project, follow these steps:

1. Clone the repository:

```bash
git clone https://github.com/microsphere-projects/microsphere-nacos.git
```

2. Build the source:

- Linux/MacOS:

```bash
./mvnw package
```

- Windows:

```powershell
mvnw.cmd package
```

## Contributing

We welcome your contributions! Please read [Code of Conduct](./CODE_OF_CONDUCT.md) before submitting a pull request.

## Reporting Issues

* Before you log a bug, please search
  the [issues](https://github.com/microsphere-projects/microsphere-nacos/issues)
  to see if someone has already reported the problem.
* If the issue doesn't already
  exist, [create a new issue](https://github.com/microsphere-projects/microsphere-nacos/issues/new).
* Please provide as much information as possible with the issue report.

## Documentation

### User Guide

[DeepWiki Host](https://deepwiki.com/microsphere-projects/microsphere-nacos)

### Wiki

[Github Host](https://github.com/microsphere-projects/microsphere-nacos/wiki)

### JavaDoc

TODO 

## License

The Microsphere Spring is released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).