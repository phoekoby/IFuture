plugins {
    id("java")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework:spring-web:6.0.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.register("prepareKotlinBuildScriptModel"){}