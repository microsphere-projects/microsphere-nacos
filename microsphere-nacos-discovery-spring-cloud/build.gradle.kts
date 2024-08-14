plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {

    // BOM
    implementation(platform(libs.spring.boot.dependencies))
    implementation(platform(libs.spring.cloud.dependencies))

    // Internal
    api(project(":microsphere-nacos-openapi"))

    // Third-Party

    // Spring Boot
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // Spring Cloud Commons
    compileOnly("org.springframework.cloud:spring-cloud-commons")

    // Testing
    testImplementation(libs.junit.jupiter.engine)

    testImplementation(libs.testcontainers)

    testImplementation(libs.testcontainers.junit.jupiter)

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(libs.logback.classic)

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}