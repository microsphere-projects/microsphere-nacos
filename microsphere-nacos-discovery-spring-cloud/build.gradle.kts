plugins {
    id("buildlogic.java-library-conventions")
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

dependencies {

    // Import the BOMs
    implementation(platform(libs.spring.boot.dependencies))
    implementation(platform(libs.spring.cloud.dependencies))

    // Internal Project
    api(project(":microsphere-nacos-openapi"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-commons")


    // Testing
    testImplementation(libs.junit.jupiter.engine)

    testImplementation(libs.testcontainers)

    testImplementation(libs.testcontainers.junit.jupiter)

    testImplementation(libs.logback.classic)
}