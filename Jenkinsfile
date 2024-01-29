/*
    Note:
    
    Windows users use "bat" instead of "sh"
    For ex: bat 'docker build -t=phuocleautoqa/selenium .'
*/
pipeline{

    agent any

    stages{

        stage('Build Jar'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Image'){
            steps{
                sh 'docker build -t=phuocleautoqa/selenium:latest .'
            }
        }

        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                sh 'echo ${DOCKER_HUB_PSW} | docker login -u ${DOCKER_HUB_USR} --password-stdin'
                sh 'docker push phuocleautoqa/selenium:latest'
                sh "docker tag phuocleautoqa/selenium:latest phuocleautoqa/selenium:${env.BUILD_NUMBER}"
                sh "docker push phuocleautoqa/selenium:${env.BUILD_NUMBER}"
            }
        }

    }

    post {
        always {
            sh 'docker logout'
        }
    }

}