---
name: scanner-gradle
description: Gradle scanner configuration for SonarQube. Use this for Java/Kotlin projects using Gradle build system.
---

# Gradle Scanner Skill

This skill provides Gradle-specific scanner documentation and configuration guidance.

## Official Documentation

### SonarQube Cloud
- https://docs.sonarsource.com/sonarqube-cloud/advanced-setup/ci-based-analysis/sonarscanner-for-gradle

### SonarQube Server
- https://docs.sonarsource.com/sonarqube-server/analyzing-source-code/scanners/sonarscanner-for-gradle

## Scanner Configuration

### build.gradle (Groovy)

```groovy
plugins {
    id "org.sonarqube" version "4.4.1.3373"
}

sonarqube {
    properties {
        property "sonar.projectKey", "my-project-key"
        property "sonar.organization", "my-org" // Cloud only
        property "sonar.host.url", "https://sonarcloud.io"
        
        // Optional: Exclusions
        property "sonar.exclusions", "**/generated/**,**/*.proto"
        
        // Optional: Coverage
        property "sonar.coverage.jacoco.xmlReportPaths", 
                 "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml"
    }
}
```

### build.gradle.kts (Kotlin)

```kotlin
plugins {
    id("org.sonarqube") version "4.4.1.3373"
}

sonarqube {
    properties {
        property("sonar.projectKey", "my-project-key")
        property("sonar.organization", "my-org") // Cloud only
        property("sonar.host.url", "https://sonarcloud.io")
        
        // Optional: Exclusions
        property("sonar.exclusions", "**/generated/**,**/*.proto")
        
        // Optional: Coverage
        property("sonar.coverage.jacoco.xmlReportPaths",
                "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")
    }
}
```

**Check latest plugin version using `web/fetch` before adding.**

### Command Line Usage

**Basic command:**
```bash
./gradlew build sonar
```

**With properties:**
```bash
./gradlew build sonar \
  -Dsonar.projectKey=my-project-key \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=$SONAR_TOKEN
```

## Environment Variables

**Required:**
- `SONAR_TOKEN`: Authentication token (or use `-Dsonar.token`)

**Optional (SonarQube Server only):**
- `SONAR_HOST_URL`: Server URL

**For SonarQube Cloud:**
- Organization must be set in build.gradle or command line

## Code Coverage

### JaCoCo Integration (Groovy)

```groovy
plugins {
    id 'jacoco'
}

jacoco {
    toolVersion = "0.8.11"
}

jacocoTestReport {
    reports {
        xml.required = true
        html.required = true
    }
}

test {
    finalizedBy jacocoTestReport
}
```

### JaCoCo Integration (Kotlin)

```kotlin
plugins {
    jacoco
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
```

Then run:
```bash
./gradlew test jacocoTestReport sonar
```

Or simply:
```bash
./gradlew build sonar
```

## Common Configuration Properties

```groovy
sonarqube {
    properties {
        // Project identification
        property "sonar.projectKey", "my-project-key"
        property "sonar.organization", "my-org" // Cloud only
        property "sonar.projectName", "My Project"
        
        // Source and test directories (auto-detected)
        // property "sonar.sources", "src/main/java"
        // property "sonar.tests", "src/test/java"
        
        // Exclusions
        property "sonar.exclusions", "**/generated/**"
        property "sonar.test.exclusions", "**/test/**/*.java"
        
        // Coverage
        property "sonar.coverage.jacoco.xmlReportPaths",
                 "${buildDir}/reports/jacoco/test/jacocoTestReport.xml"
        
        // Code duplication
        property "sonar.cpd.exclusions", "**/model/**,**/dto/**"
        
        // Language specific
        property "sonar.java.source", "17"
        property "sonar.java.target", "17"
    }
}
```

## Multi-Module Projects

For multi-module projects, add plugin to root build.gradle:

```groovy
// Root build.gradle
plugins {
    id "org.sonarqube" version "4.4.1.3373"
}

subprojects {
    apply plugin: 'org.sonarqube'
}

sonarqube {
    properties {
        property "sonar.projectKey", "my-project-key"
        property "sonar.organization", "my-org"
    }
}
```

Then run from root:
```bash
./gradlew build sonar
```

## Best Practices

1. **Use Gradle wrapper**: Always use `./gradlew` for consistency
2. **Add JaCoCo**: Enable code coverage reporting
3. **Build first**: Run `build` task before `sonar`
4. **Properties in build.gradle**: Store non-sensitive config in build file
5. **Secrets as env vars**: Pass SONAR_TOKEN via environment variables
6. **Plugin version**: Keep SonarQube plugin updated
7. **Kotlin DSL**: Use .kts for type-safe configuration

## Gradle Version Compatibility

- SonarQube Gradle plugin requires Gradle 7.3+
- For older Gradle versions, use earlier plugin versions
- Check compatibility in official documentation

## Platform Integration

See platform-specific skills for CI/CD integration:
- **platform-github-actions**: GitHub Actions with Gradle
- **platform-gitlab-ci**: GitLab CI with Gradle
- **platform-azure-devops**: Azure Pipelines with Gradle
- **platform-bitbucket**: Bitbucket Pipelines with Gradle

## Usage Instructions

**For SonarArchitectGuide:**
- Include documentation link in responses
- Explain Gradle concepts when needed

**For SonarArchitectLight:**
- Use `web/fetch` to check latest plugin/JaCoCo versions
- Add plugin to build.gradle or build.gradle.kts
- Configure sonarqube block with project properties
- Configure CI/CD command: `./gradlew build sonar`
- Do NOT include links in responses
