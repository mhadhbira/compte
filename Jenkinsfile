pipeline {
    agent any
    /* agent {
        docker {
            image 'maven:3.6.3-openjdk-17'
            //args '-v $HOME/.m2:/root/.m2' // spécifier un répertoire pour que Maven stocke ses fichiers dans un endroit où il a les permissions
            args '--user root'
        }
    } */
    environment {
        //MAVEN_OPTS = "-Dmaven.repo.local=/tmp/.m2/repository"
        dockerHome = tool 'myDocker'
        mavenHome = tool 'myMaven'
        PATH = "$dockerHome/bin:$mavenHome/bin:$PATH" // build folder of dockerhome added to the path
    }
    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git branch: 'master', url: 'https://github.com/mhadhbira/compte.git'
            }
        }
        stage('Build') {
            steps {
                // Crée le répertoire .m2 avec les permissions nécessaires
               // sh 'mkdir -p /root/.m2 && chmod -R 777 /root/.m2'
                // Build the Spring Boot application with Maven
                 sh 'chmod +x mvnw'
                 sh './mvnw --version'
                 sh 'docker --version'
                 sh 'chmod -R 777 /var/jenkins_home/workspace/compte-service-pipeline-1/target || true'  // Ensure the target directory is writable
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
        stage('Package') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }
        stage('Build Docker image') {
            steps{
              //docker build -t ranyamh/compte-service:1.0.1 .
              script {   
                // Unset DOCKER_HOST to avoid connection issues
                sh 'unset DOCKER_HOST'           
                dockerImage = docker.build("ranyamh/compte-service:${env.BUILD_TAG}")
              }  
            }            
        }
        stage('Push docker image') {
            //docker push ranyamh/compte-service:1.0.1
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub'){
                        dockerImage.push();
                        dockerImage.push('1.0.1');
                    }                   
                }
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
