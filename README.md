# Scalable Test Automation Platform using Selenium Grid and Docker

A robust, scalable test automation framework leveraging Selenium Grid with Docker containerization for cross-browser testing, featuring Jenkins CI/CD integration and comprehensive reporting.

![Java](https://img.shields.io/badge/Java-11-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.25.0-green)
![Docker](https://img.shields.io/badge/Docker-Latest-blue)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Maven](https://img.shields.io/badge/Maven-Build-yellow)
![Jenkins](https://img.shields.io/badge/Jenkins-CI/CD-lightblue)

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [CI/CD Pipeline](#cicd-pipeline)
- [Reports](#reports)
- [Docker Setup](#docker-setup)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Overview

This project demonstrates a production-ready test automation platform designed for scalability and maintainability. It uses Selenium Grid to enable parallel test execution across multiple browsers and environments, containerized with Docker for consistent deployment.

### Key Highlights:
- **Cross-browser testing** with Chrome and Firefox
- **Parallel execution** using Selenium Grid
- **Containerized environment** with Docker Compose
- **CI/CD integration** with Jenkins pipeline
- **Rich reporting** with ExtentReports
- **Screenshot capture** on test failures
- **TestNG framework** for test organization

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Jenkins       │    │  Selenium Hub   │    │   Test Reports  │
│   Pipeline      │───▶│  (Port 4444)    │───▶│  ExtentReports  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
            ┌───────▼──────┐      ┌────────▼────────┐
            │ Chrome Node  │      │ Firefox Node    │
            │ Container    │      │ Container       │
            └──────────────┘      └─────────────────┘
```

## Features

### Test Framework Features
- **Page Object Model** design pattern
- **Data-driven testing** capabilities
- **Cross-browser testing** (Chrome, Firefox)
- **Parallel test execution**
- **Test retry mechanism**
- **Custom reporting**

### DevOps Features
- **Docker containerization**
- **Jenkins CI/CD pipeline**
- **Automated test execution**
- **Report archival and publishing**
- **Environment cleanup**

### Reporting Features
- **ExtentReports** with detailed test results
- **Screenshot capture** on failures
- **Test execution timeline**
- **Browser-wise test distribution**

## Prerequisites

### Required Software:
- **Java 11** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **Jenkins** (for CI/CD)
- **Git**

### Verify Installation:
```bash
java -version
mvn -version
docker --version
docker-compose --version
```

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Shrinidhi972004/Scalable-Test-Automation-Platform-using-Selenium-Grid-and-Docker.git
cd Scalable-Test-Automation-Platform-using-Selenium-Grid-and-Docker
```

### 2. Start Selenium Grid
```bash
docker-compose up -d
```

### 3. Verify Grid Setup
```bash
# Check containers are running
docker ps

# Access Grid console
open http://localhost:4444
```

### 4. Run Tests
```bash
mvn clean test
```

### 5. View Reports
```bash
# Open the generated report
open target/ExtentReport.html
```

## Project Structure

```
selenium-grid-demo/
├── README.md                    # Project documentation
├── docker-compose.yml           # Selenium Grid configuration
├── Jenkinsfile                  # CI/CD pipeline definition
├── pom.xml                      # Maven dependencies
├── testng.xml                   # TestNG suite configuration
├── src/
│   ├── main/java/com/testing/
│   │   └── App.java              # Main application class
│   └── test/java/com/testing/
│       ├── SwagLabsTest.java     # Test scenarios
│       └── ExtentManager.java    # Report configuration
└── target/                       # Build artifacts and reports
    ├── ExtentReport.html         # Test execution report
    ├── screenshots/              # Failure screenshots
    └── surefire-reports/         # TestNG XML reports
```

## Configuration

### Docker Compose Configuration
The `docker-compose.yml` file defines:
- **Selenium Hub** on port 4444
- **Chrome Node** with 2GB shared memory
- **Firefox Node** with 2GB shared memory

### TestNG Configuration
The `testng.xml` configures:
- Parallel execution by tests
- Browser parameters (chrome, firefox)
- Test class mappings

### Maven Dependencies
Key dependencies include:
- Selenium Java 4.25.0
- TestNG 7.10.2
- ExtentReports 5.0.9
- Commons IO 2.15.1

## Running Tests

### Local Execution

#### 1. Start Grid Infrastructure
```bash
docker-compose up -d
```

#### 2. Run All Tests
```bash
mvn clean test
```

#### 3. Run Specific Browser Tests
```bash
# Chrome only
mvn test -Dtest=SwagLabsTest -Dbrowser=chrome

# Firefox only
mvn test -Dtest=SwagLabsTest -Dbrowser=firefox
```

#### 4. Run with Custom TestNG Suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Available Test Scenarios

#### Test Cases
1. **Valid Login & Add to Cart**
   - Login with valid credentials
   - Add product to cart
   - Verify cart contents

2. **Invalid Login**
   - Attempt login with invalid credentials
   - Verify error message display

### Grid Monitoring
- **Grid Console**: http://localhost:4444
- **Node Status**: Check available browsers and sessions

## CI/CD Pipeline

The Jenkins pipeline (`Jenkinsfile`) includes:

### Pipeline Stages:
1. **Clean Workspace** - Removes previous build artifacts
2. **Checkout Code** - Pulls latest code from repository
3. **Start Selenium Grid** - Launches Docker containers
4. **Run Tests** - Executes Maven test suite
5. **Stop Selenium Grid** - Cleans up containers
6. **Archive Reports** - Stores test reports and screenshots

### Pipeline Features:
- **Automated cleanup** before and after tests
- **Report publishing** with ExtentReports
- **Screenshot archival** for failed tests
- **Status notifications** (success/failure)

### Jenkins Setup:
```groovy
pipeline {
    agent any
    environment {
        DOCKER_COMPOSE_FILE = "docker-compose.yml"
        REPORT_DIR = "target"
    }
    // ... pipeline stages
}
```

## Reports

### ExtentReports Features:
- **Test execution summary**
- **Pass/Fail status with timestamps**
- **Screenshot integration** for failures
- **Browser-wise test distribution**
- **Detailed stack traces** for failures

### Report Locations:
- **HTML Report**: `target/ExtentReport.html`
- **Screenshots**: `target/screenshots/`
- **TestNG Reports**: `target/surefire-reports/`

### Sample Report Sections:
```
Dashboard
├── Test Statistics
├── Execution Timeline  
├── Browser Distribution
└── Failure Analysis
```

## Docker Setup

### Container Configuration:

#### Selenium Hub
```yaml
selenium-hub:
  image: selenium/hub:4.25.0
  ports:
    - "4444:4444"
```

#### Browser Nodes
```yaml
chrome:
  image: selenium/node-chrome:4.25.0
  shm_size: 2gb
  depends_on:
    - selenium-hub
```

### Useful Docker Commands:
```bash
# Start grid
docker-compose up -d

# View logs
docker-compose logs selenium-hub
docker-compose logs chrome

# Scale nodes
docker-compose up -d --scale chrome=3 --scale firefox=2

# Stop grid
docker-compose down -v

# Clean up
docker system prune -f
```

## Troubleshooting

### Common Issues and Solutions:

#### 1. **Grid Not Starting**
```bash
# Check port conflicts
netstat -tulpn | grep 4444

# Check Docker status
docker ps
docker-compose logs
```

#### 2. **Tests Failing to Connect**
```bash
# Verify grid accessibility
curl http://localhost:4444/status

# Check firewall settings
sudo ufw status
```

#### 3. **Out of Memory Errors**
```bash
# Increase Docker memory allocation
# Edit Docker Desktop > Resources > Memory
# Or increase shm_size in docker-compose.yml
```

#### 4. **Build Failures**
```bash
# Clean Maven cache
mvn clean install -U

# Check Java version
java -version
mvn -version
```

### Debug Mode:
```bash
# Run with debug logging
mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

# Enable Selenium debug
mvn test -Dselenium.logging.level=DEBUG
```

## Contributing

### Development Workflow:
1. **Fork** the repository
2. **Create** a feature branch
3. **Implement** changes with tests
4. **Run** the test suite
5. **Submit** a pull request

### Code Standards:
- Follow **Java coding conventions**
- Include **JavaDoc** for public methods
- Add **unit tests** for new features
- Update **documentation** as needed

### Pull Request Checklist:
- [ ] Tests pass locally
- [ ] Code follows project standards
- [ ] Documentation updated
- [ ] Screenshots for UI changes

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Authors

- **Shrinidhi** - *Initial work* - [Shrinidhi972004](https://github.com/Shrinidhi972004)

## Acknowledgments

- **Selenium** team for the excellent WebDriver framework
- **Docker** for containerization capabilities  
- **TestNG** for the testing framework
- **ExtentReports** for comprehensive reporting
- **Jenkins** for CI/CD automation

## Support

For support and questions:
- **Email**: Create an issue in the repository
- **Documentation**: Check the wiki section
- **Discussions**: Use GitHub discussions

---

**Star this repository** if you find it helpful!

**Repository**: [Scalable-Test-Automation-Platform-using-Selenium-Grid-and-Docker](https://github.com/Shrinidhi972004/Scalable-Test-Automation-Platform-using-Selenium-Grid-and-Docker)
