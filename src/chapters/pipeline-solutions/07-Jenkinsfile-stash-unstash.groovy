pipeline {
  agent {
    node {
      label 'maven3-jdk8'
    }
  }
  stages {
    stage('Build') {
      steps {
        sh './scripts/build.sh'
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Test') {
      steps {
        sh './scripts/integration-tests.sh'
        junit 'target/failsafe-reports/*.xml'
      }
    }
    stage('Deploy') {
      agent {
        node {
          label 'production'
        }
      }
      steps {
        sh './scripts/deploy.sh'
      }
    }
  }
}
