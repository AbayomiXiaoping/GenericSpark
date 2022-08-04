// Credential ID's

def credentialID = 'GITSSH'
def gitURL = 'https://github.com/AbayomiXiaoping/spark.git'

def artifactURL() {

def scalaVersion = "2.11"

def url = "https://shaktimaan.jfrog.io/artifactory/abayomi-spark-sbt-dev/"

return url

}

pipeline{

agent{
   label 'l4'
}

parameters {

string defaultValue: 'master', name : 'BRANCH_NAME'
}

stages{
 stage('Clean Workspace'){
 steps{
   echo "GIT Checkout"
   git branch: "${params.BRANCH_NAME}", credentialsId: "${credentialID}", url: "${gitURL}"
 }
 }

 stage('SBT Publish'){
 steps{
 withCredentials()
 }
 }

}

}