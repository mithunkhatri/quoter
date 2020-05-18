pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        echo 'Begin'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }

  }
}