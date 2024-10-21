pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git branch: 'master', url: 'https://github.com/mhadhbira/compte.git'
            }
        }
        
        stage('Build') {
            steps {
                // Build the Spring Boot application with Maven
                 sh 'chmod +x mvnw'
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                 sh 'chmod +x mvnw'
                // Run the tests
                sh './mvnw test'
            }
        }
    }

    post{
        always {
            echo 'I run always'
        }
        success {
            echo 'I run when you are successful'
        }
        failure {
            echo 'I run when you faile :('
        }
    }
}
