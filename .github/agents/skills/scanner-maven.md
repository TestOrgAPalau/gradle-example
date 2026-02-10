---
name: scanner-maven
description: Maven scanner configuration for SonarQube. Use this for Java projects using Maven build system.
---

# Maven Scanner Skill

This skill provides Maven-specific scanner documentation and configuration guidance.

## Official Documentation

### SonarQube Cloud
- https://docs.sonarsource.com/sonarqube-cloud/advanced-setup/ci-based-analysis/sonarscanner-for-maven

### SonarQube Server
- https://docs.sonarsource.com/sonarqube-server/analyzing-source-code/scanners/sonarscanner-for-maven

## Scanner Configuration

### Command Line Usage

**Basic command:**
```bash
mvn clean verify sonar:sonar
```

**With properties:**
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=my-project-key \
  -Dsonar.projectName="My Project" \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=$SONAR_TOKEN
```

### pom.xml Configuration (Optional)

You can add SonarQube properties directly to `pom.xml`:

```xml
<properties>
  <sonar.organization>my-org</sonar.organization>
  <sonar.host.url>https://sonarcloud.io</sonar.host.url>
  <sonar.projectKey>my-project-key</sonar.projectKey>
  
  <!-- Optional: Exclude patterns -->
  <sonar.exclusions>**/generated/**,**/test/**</sonar.exclusions>
  
  <!-- Optional: Coverage -->
  <sonar.coverage.jacoco.xmlReportPaths>
    ${project.build.directory}/site/jacoco/jacoco.xml
  </sonar.coverage.jacoco.xmlReportPaths>
</properties>
```

### Plugin Version

The Maven plugin is automatically downloaded. To specify a version:

```xml
<build>
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.sonarsource.scanner.maven</groupId>
        <artifactId>sonar-maven-plugin</artifactId>
        <version>3.11.0.3922</version>
      </plugin>
    </plugins>
  </pluginManagement>
</build>
```

**Check latest version using `web/fetch` before specifying.**

## Environment Variables

**Required:**
- `SONAR_TOKEN`: Authentication token

**Optional (SonarQube Server only):**
- `SONAR_HOST_URL`: Server URL (not needed for Cloud)

**For SonarQube Cloud:**
- Properties can be set in pom.xml or passed as command arguments

## Code Coverage

### JaCoCo Integration

Add JaCoCo plugin to generate coverage:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.11</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>test</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Then run:
```bash
mvn clean verify sonar:sonar
```

## Common Configuration Properties

```properties
# Project identification
sonar.projectKey=my-project-key
sonar.organization=my-org
sonar.projectName=My Project

# Source and test directories (auto-detected by Maven)
# sonar.sources=src/main/java
# sonar.tests=src/test/java

# Exclusions
sonar.exclusions=**/generated/**,**/*.proto

# Coverage reports
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml

# Code duplication
sonar.cpd.exclusions=**/model/**,**/dto/**
```

## Multi-Module Projects

Maven automatically handles multi-module projects. Run from parent directory:

```bash
mvn clean verify sonar:sonar
```

Each module will be analyzed as a separate component in SonarQube.

## Best Practices

1. **Use `verify` not `install`**: Run tests without installing to local repo
2. **Enable JaCoCo**: Add JaCoCo plugin for code coverage
3. **Clean before analysis**: Always run `clean` to ensure fresh build
4. **Properties in pom.xml**: Store non-sensitive config in pom.xml
5. **Secrets as env vars**: Pass SONAR_TOKEN via environment variables
6. **Maven wrapper**: Use `./mvnw` for consistent Maven versions

## Platform Integration

See platform-specific skills for CI/CD integration:
- **platform-github-actions**: GitHub Actions with Maven
- **platform-gitlab-ci**: GitLab CI with Maven
- **platform-azure-devops**: Azure Pipelines with Maven
- **platform-bitbucket**: Bitbucket Pipelines with Maven

## Usage Instructions

**For SonarArchitectGuide:**
- Include documentation link in responses
- Explain Maven concepts when needed

**For SonarArchitectLight:**
- Use `web/fetch` to check latest plugin/JaCoCo versions
- Create/update pom.xml if needed
- Configure CI/CD command: `mvn clean verify sonar:sonar`
- Do NOT include links in responses
