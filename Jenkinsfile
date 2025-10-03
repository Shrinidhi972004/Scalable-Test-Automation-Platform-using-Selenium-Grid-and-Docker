pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = "docker-compose.yml"
        REPORT_DIR = "target"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/Shrinidhi972004/Scalable-Test-Automation-Platform-using-Selenium-Grid-and-Docker.git'
            }
        }

        stage('Start Selenium Grid') {
            steps {
                sh '''
                    echo "Starting Selenium Grid..."
                    docker-compose -f $DOCKER_COMPOSE_FILE down -v || true
                    docker-compose -f $DOCKER_COMPOSE_FILE up -d
                    sleep 15   # wait for hub & nodes to register
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    echo "Running Maven Tests..."
                    mvn clean test
                '''
            }
        }

        stage('Stop Selenium Grid') {
            steps {
                sh '''
                    echo "Stopping Selenium Grid..."
                    docker-compose -f $DOCKER_COMPOSE_FILE down -v
                '''
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: "$REPORT_DIR/ExtentReport.html, $REPORT_DIR/screenshots/**", fingerprint: true
                publishHTML([reportDir: "$REPORT_DIR",
                             reportFiles: 'ExtentReport.html',
                             reportName: 'Extent Report'])
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        failure {
            echo "❌ Tests failed. Check ExtentReport.html in artifacts."
        }
        success {
            echo "✅ Tests passed. Report archived."
        }
    }
}
