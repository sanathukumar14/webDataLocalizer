pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t web-data-localizer .'
            }
        }
        stage('Push Docker Image') {
            steps {
                sh 'docker tag web-data-localizer:latest sk8005/web-data-localizer:latest'
                sh 'docker push sk8005/web-data-localizer:latest'
            }
        }
    }
}