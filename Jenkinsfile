// Jenkinsfile for E-Commerce Test Automation

pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'edge', 'firefox'],
            description: 'Select browser to run tests'
        )
        string(
            name: 'TIMEOUT',
            defaultValue: '80',
            description: 'Implicit wait timeout in seconds'
        )
    }

    environment {
        JAVA_HOME = "${tool 'Java11'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        WORKSPACE_DIR = "${WORKSPACE}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== Checking out code =========='
                checkout scm
                sh 'git log --oneline -1'
            }
        }

        stage('Setup') {
            steps {
                echo '========== Setting up environment =========='
                script {
                    sh '''
                        echo "Java version:"
                        java -version
                        echo "Maven version:"
                        mvn --version
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                echo '========== Building project =========='
                script {
                    try {
                        sh './mvnw.cmd clean compile -q'
                        echo '✓ Build successful'
                    } catch (Exception e) {
                        error("Build failed: ${e.message}")
                    }
                }
            }
        }

        stage('Configure') {
            steps {
                echo '========== Configuring test environment =========='
                script {
                    sh '''
                        echo "Setting browser to: ${BROWSER}"
                        sed -i 's/browser = .*/browser = '${BROWSER}'/' testData.properties
                        cat testData.properties
                    '''
                }
            }
        }

        stage('Run Tests') {
            steps {
                echo '========== Running test suite =========='
                script {
                    try {
                        sh './mvnw.cmd test -DsuiteXmlFile=testng.xml'
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "Tests completed with failures: ${e.message}"
                    }
                }
            }
        }

        stage('Generate Report') {
            steps {
                echo '========== Generating test reports =========='
                script {
                    try {
                        sh './mvnw.cmd surefire-report:report'
                    } catch (Exception e) {
                        echo "Report generation warning: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo '========== Post-build actions =========='

            // Archive test logs
            archiveArtifacts artifacts: 'test-logs/**/*.log', allowEmptyArchive: true

            // Publish test results
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

            // Publish HTML report
            publishHTML([
                reportDir: 'target/site',
                reportFiles: 'surefire-report.html',
                reportName: 'Surefire Report',
                keepAll: true
            ])

            // Clean workspace
            deleteDir()
        }

        success {
            echo '✓ Pipeline completed successfully'
            script {
                // Send success notification
                emailext(
                    subject: "✓ Tests Passed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: """
                        Tests executed successfully!

                        Job: ${env.JOB_NAME}
                        Build: ${env.BUILD_NUMBER}
                        Duration: ${currentBuild.durationString}
                        Browser: ${params.BROWSER}

                        View Results: ${env.BUILD_URL}
                    """,
                    to: '${DEFAULT_RECIPIENTS}'
                )
            }
        }

        failure {
            echo '✗ Pipeline failed'
            script {
                // Send failure notification
                emailext(
                    subject: "✗ Tests Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: """
                        Tests execution failed!

                        Job: ${env.JOB_NAME}
                        Build: ${env.BUILD_NUMBER}
                        Duration: ${currentBuild.durationString}
                        Browser: ${params.BROWSER}

                        View Logs: ${env.BUILD_URL}/console
                        View Results: ${env.BUILD_URL}
                    """,
                    to: '${DEFAULT_RECIPIENTS}'
                )
            }
        }

        unstable {
            echo '⚠ Pipeline unstable (some tests failed)'
            script {
                // Send unstable notification
                emailext(
                    subject: "⚠ Tests Unstable: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: """
                        Some tests failed during execution!

                        Job: ${env.JOB_NAME}
                        Build: ${env.BUILD_NUMBER}
                        Duration: ${currentBuild.durationString}
                        Browser: ${params.BROWSER}

                        View Results: ${env.BUILD_URL}
                    """,
                    to: '${DEFAULT_RECIPIENTS}'
                )
            }
        }
    }
}

