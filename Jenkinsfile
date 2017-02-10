#!groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                withMaven {
                    bat "mvn clean install"
                }
            }
        }
        stage('Test'){
            steps {
                echo 'Testing..'
            }
        }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('Kiot-SonarQube') { // Map to SonarQube servers "Name" in Configure System
                    // requires SonarQube Scanner for Maven 3.2+
                    bat 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
