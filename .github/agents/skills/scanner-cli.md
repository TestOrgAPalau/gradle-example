---
name: scanner-cli
description: SonarScanner CLI configuration for SonarQube. Use this for JavaScript, TypeScript, Python, Go, PHP, and other languages not using Maven/Gradle/.NET.
---

# SonarScanner CLI Skill

This skill provides SonarScanner CLI documentation and configuration guidance for languages like JavaScript, TypeScript, Python, Go, PHP, Ruby, and others.

## Official Documentation

### SonarQube Cloud
- https://docs.sonarsource.com/sonarqube-cloud/advanced-setup/ci-based-analysis/sonarscanner-cli

### SonarQube Server
- https://docs.sonarsource.com/sonarqube-server/analyzing-source-code/scanners/sonarscanner

## Scanner Installation

### Download and Install
Download from official SonarQube documentation based on your OS.

### Docker Image
```bash
docker run --rm \
  -e SONAR_HOST_URL="https://sonarcloud.io" \
  -e SONAR_TOKEN="$SONAR_TOKEN" \
  -v "$(pwd):/usr/src" \
  sonarsource/sonar-scanner-cli
```

### NPM/Yarn (for JavaScript/TypeScript projects)
```bash
npm install -g sonarqube-scanner
# or
yarn global add sonarqube-scanner
```

## Configuration File: sonar-project.properties

**Required for CLI scanner.** Create in project root:

### Basic Configuration

```properties
# Project identification
sonar.projectKey=my-project-key
sonar.projectName=My Project
sonar.projectVersion=1.0

# Source code location
sonar.sources=src
sonar.tests=test

# Encoding
sonar.sourceEncoding=UTF-8
```

### SonarQube Cloud Configuration

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org
sonar.host.url=https://sonarcloud.io

sonar.sources=src
sonar.tests=test
```

### SonarQube Server Configuration

```properties
sonar.projectKey=my-project-key
sonar.host.url=https://sonar.mycompany.com

sonar.sources=src
sonar.tests=test
```

## Language-Specific Configuration

### JavaScript/TypeScript

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org

# Source files
sonar.sources=src
sonar.tests=src
sonar.test.inclusions=**/*.test.js,**/*.test.ts,**/*.spec.js,**/*.spec.ts

# Exclusions
sonar.exclusions=**/node_modules/**,**/dist/**,**/build/**,**/coverage/**
sonar.test.exclusions=**/*.test.js,**/*.test.ts,**/*.spec.js,**/*.spec.ts

# Coverage (Jest example)
sonar.javascript.lcov.reportPaths=coverage/lcov.info
sonar.typescript.lcov.reportPaths=coverage/lcov.info

# ESLint report (optional)
sonar.eslint.reportPaths=eslint-report.json
```

**Run tests with coverage first:**
```bash
npm test -- --coverage
sonar-scanner
```

### Python

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org

# Source files
sonar.sources=src
sonar.tests=tests

# Exclusions
sonar.exclusions=**/venv/**,**/__pycache__/**,**/*.pyc

# Coverage (coverage.py or pytest-cov)
sonar.python.coverage.reportPaths=coverage.xml

# Python version
sonar.python.version=3.11
```

**Run tests with coverage first:**
```bash
pytest --cov=src --cov-report=xml
sonar-scanner
```

### Go

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org

# Source files
sonar.sources=.
sonar.tests=.
sonar.test.inclusions=**/*_test.go

# Exclusions
sonar.exclusions=**/vendor/**
sonar.test.exclusions=**/*_test.go

# Coverage
sonar.go.coverage.reportPaths=coverage.out
```

**Run tests with coverage first:**
```bash
go test -coverprofile=coverage.out ./...
sonar-scanner
```

### PHP

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org

# Source files
sonar.sources=src
sonar.tests=tests

# Exclusions
sonar.exclusions=**/vendor/**,**/cache/**

# Coverage (PHPUnit)
sonar.php.coverage.reportPaths=coverage.xml

# PHP version
sonar.php.version=8.1
```

### Ruby

```properties
sonar.projectKey=my-project-key
sonar.organization=my-org

# Source files
sonar.sources=lib,app
sonar.tests=spec,test

# Exclusions
sonar.exclusions=**/vendor/**,**/tmp/**

# Coverage (SimpleCov)
sonar.ruby.coverage.reportPaths=coverage/.resultset.json
```

## Command Line Usage

### Basic Scan
```bash
sonar-scanner \
  -Dsonar.projectKey=my-project-key \
  -Dsonar.sources=. \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=$SONAR_TOKEN
```

### With Properties File
If `sonar-project.properties` exists:
```bash
sonar-scanner -Dsonar.token=$SONAR_TOKEN
```

### Debug Mode
```bash
sonar-scanner -X -Dsonar.token=$SONAR_TOKEN
```

## Common Configuration Properties

```properties
# Project identification
sonar.projectKey=my-project-key
sonar.organization=my-org
sonar.projectName=My Project
sonar.projectVersion=1.0

# Source and test paths
sonar.sources=src,lib
sonar.tests=test,spec
sonar.test.inclusions=**/*.test.js,**/*_test.go

# Exclusions and inclusions
sonar.exclusions=**/node_modules/**,**/vendor/**,**/*.min.js
sonar.coverage.exclusions=**/*.test.js,**/test/**

# Coverage reports
sonar.javascript.lcov.reportPaths=coverage/lcov.info
sonar.python.coverage.reportPaths=coverage.xml
sonar.go.coverage.reportPaths=coverage.out

# Encoding
sonar.sourceEncoding=UTF-8

# Branch analysis (for PRs)
sonar.pullrequest.key=123
sonar.pullrequest.branch=feature/my-feature
sonar.pullrequest.base=main
```

## Code Coverage Setup

Most languages require running tests with coverage before scanning:

**JavaScript/TypeScript (Jest):**
```bash
npm test -- --coverage
sonar-scanner
```

**Python (pytest):**
```bash
pytest --cov=src --cov-report=xml
sonar-scanner
```

**Go:**
```bash
go test -coverprofile=coverage.out ./...
sonar-scanner
```

## Best Practices

1. **Create sonar-project.properties**: Required for CLI scanner
2. **Run coverage first**: Always generate coverage reports before scanning
3. **Exclude dependencies**: Exclude node_modules, vendor, venv directories
4. **Token security**: Use environment variable for SONAR_TOKEN
5. **Specify source paths**: Explicitly set sonar.sources
6. **Test inclusions**: Define test file patterns clearly
7. **Check encoding**: Set sonar.sourceEncoding if not UTF-8

## Platform Integration

Most CI/CD platforms provide official actions/pipes for CLI scanner:

- **platform-github-actions**: Use `sonarsource/sonarqube-scan-action`
- **platform-gitlab-ci**: Use `sonarsource/sonar-scanner-cli` Docker image
- **platform-azure-devops**: Use SonarQube CLI task
- **platform-bitbucket**: Use SonarCloud/SonarQube scan pipes

See platform-specific skills for integration details.

## Environment Variables

**Required:**
- `SONAR_TOKEN`: Authentication token

**Optional:**
- `SONAR_HOST_URL`: Can be in properties file instead
- `SONAR_ORGANIZATION`: Can be in properties file instead

## Troubleshooting

### Scanner not finding sources
- Check `sonar.sources` path is correct
- Verify path is relative to project root

### Coverage not reported
- Ensure coverage file exists and path matches property
- Check coverage report format is correct for language

### Encoding issues
- Set `sonar.sourceEncoding=UTF-8` explicitly

## Usage Instructions

**For SonarArchitectGuide:**
- Include documentation link in responses
- Explain sonar-project.properties structure
- Mention coverage setup for specific language

**For SonarArchitectLight:**
- Create sonar-project.properties with appropriate settings
- Include language-specific coverage configuration
- Configure CI/CD to run tests before scan
- Do NOT include links in responses
