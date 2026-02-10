---
name: scanner-dotnet
description: .NET scanner configuration for SonarQube. Use this for C#, VB.NET, and F# projects.
---

# .NET Scanner Skill

This skill provides .NET-specific scanner documentation and configuration guidance.

## Official Documentation

### SonarQube Server
- https://docs.sonarsource.com/sonarqube-server/analyzing-source-code/scanners/dotnet/using

### SonarQube Cloud
- SonarQube Cloud uses the same .NET scanner as Server

## Scanner Installation

### Global Tool Installation
```bash
dotnet tool install --global dotnet-sonarscanner
```

### Update Existing Installation
```bash
dotnet tool update --global dotnet-sonarscanner
```

### Local Tool Installation (recommended for CI/CD)
```bash
dotnet new tool-manifest
dotnet tool install dotnet-sonarscanner
```

Then use:
```bash
dotnet tool run dotnet-sonarscanner begin ...
```

## Scanner Usage

### Basic Three-Step Process

**1. Begin Analysis:**
```bash
dotnet sonarscanner begin \
  /k:"project-key" \
  /d:sonar.host.url="https://sonarcloud.io" \
  /d:sonar.token="$SONAR_TOKEN"
```

**2. Build Project:**
```bash
dotnet build
```

**3. End Analysis:**
```bash
dotnet sonarscanner end \
  /d:sonar.token="$SONAR_TOKEN"
```

### SonarQube Cloud Configuration

```bash
dotnet sonarscanner begin \
  /k:"project-key" \
  /o:"my-organization" \
  /d:sonar.host.url="https://sonarcloud.io" \
  /d:sonar.token="$SONAR_TOKEN"
  
dotnet build

dotnet sonarscanner end \
  /d:sonar.token="$SONAR_TOKEN"
```

### SonarQube Server Configuration

```bash
dotnet sonarscanner begin \
  /k:"project-key" \
  /d:sonar.host.url="https://sonar.mycompany.com" \
  /d:sonar.token="$SONAR_TOKEN"
  
dotnet build

dotnet sonarscanner end \
  /d:sonar.token="$SONAR_TOKEN"
```

## Code Coverage

### Using coverlet for Coverage

**Install coverlet:**
```bash
dotnet add package coverlet.msbuild
```

**Collect coverage during tests:**
```bash
dotnet test /p:CollectCoverage=true \
  /p:CoverletOutputFormat=opencover \
  /p:CoverletOutput=./coverage/
```

**Full workflow with coverage:**
```bash
# Begin analysis
dotnet sonarscanner begin \
  /k:"project-key" \
  /d:sonar.host.url="https://sonarcloud.io" \
  /d:sonar.token="$SONAR_TOKEN" \
  /d:sonar.cs.opencover.reportsPaths="**/coverage.opencover.xml"

# Build
dotnet build

# Test with coverage
dotnet test /p:CollectCoverage=true \
  /p:CoverletOutputFormat=opencover \
  /p:CoverletOutput=./coverage/

# End analysis
dotnet sonarscanner end \
  /d:sonar.token="$SONAR_TOKEN"
```

### Using VSTest for Coverage

```bash
dotnet test --collect:"XPlat Code Coverage"

dotnet sonarscanner begin \
  /k:"project-key" \
  /d:sonar.cs.vstest.reportsPaths="**/*.trx" \
  /d:sonar.cs.vscoveragexml.reportsPaths="**/*.coveragexml"
```

## Common Configuration Parameters

```bash
# Project identification
/k:"project-key"                          # Required
/n:"Project Name"                         # Optional
/v:"1.0"                                  # Version

# Organization (Cloud only)
/o:"organization-key"

# Host and authentication
/d:sonar.host.url="https://sonarcloud.io"
/d:sonar.token="$SONAR_TOKEN"

# Coverage reports
/d:sonar.cs.opencover.reportsPaths="**/coverage.opencover.xml"
/d:sonar.cs.vscoveragexml.reportsPaths="**/*.coveragexml"

# Exclusions
/d:sonar.exclusions="**/obj/**,**/bin/**,**/*.Generated.cs"
/d:sonar.test.exclusions="**/*Tests/**"

# Language version
/d:sonar.cs.roslyn.ignoreIssues="false"
```

## Solution with Multiple Projects

Scanner automatically detects all projects in a solution:

```bash
dotnet sonarscanner begin /k:"project-key" /d:sonar.token="$SONAR_TOKEN"
dotnet build MySolution.sln
dotnet test MySolution.sln
dotnet sonarscanner end /d:sonar.token="$SONAR_TOKEN"
```

## Common Issues

### Issue: Scanner not found
**Solution:** Install globally or use local tool manifest

### Issue: No coverage reported
**Solution:** Ensure coverage format is correct and path matches

### Issue: Build output not found
**Solution:** Must run `dotnet build` between begin/end

## Best Practices

1. **Use local tool manifest**: Better for reproducible builds in CI/CD
2. **Collect coverage**: Always include code coverage
3. **Token security**: Use environment variables for tokens
4. **Build before end**: Always run build between begin/end
5. **Specify paths**: Use explicit coverage report paths
6. **Update regularly**: Keep scanner tool updated
7. **Exclude generated code**: Exclude bin/, obj/, and generated files

## Platform Integration

See platform-specific skills for CI/CD integration:
- **platform-github-actions**: GitHub Actions with .NET
- **platform-gitlab-ci**: GitLab CI with .NET
- **platform-azure-devops**: Azure Pipelines with .NET
- **platform-bitbucket**: Bitbucket Pipelines with .NET

## Environment Variables

**Required:**
- `SONAR_TOKEN`: Authentication token

**Optional:**
- `SONAR_HOST_URL`: Can be passed as parameter instead
- `SONAR_ORGANIZATION`: Can be passed as parameter instead

## Usage Instructions

**For SonarArchitectGuide:**
- Include documentation link in responses
- Explain .NET scanner three-step process
- Mention code coverage setup

**For SonarArchitectLight:**
- Use `web/fetch` to check if scanner version info is available
- Create CI/CD scripts with begin/build/end pattern
- Include coverage collection if tests detected
- Do NOT include links in responses
