#!/usr/bin/env groovy

def url = "git@git.cityos.com:smart-cityos/datav_center.git"
def git_credentialsId = "fde9d8ee-6b45-4fc0-ac10-31f31536f640"
def server = "http://192.168.10.11:8080"
def registry = "reg.icity-os.com"
def registry_credentialsId = "2e8c4a97-ac7b-4779-a0da-a505548c6575"

node('node-kubectl') {
  stage 'checkout'
  git url: url, credentialsId: git_credentialsId

  stage 'login'
  withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: registry_credentialsId,
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
    sh "docker login --username $USERNAME --password $PASSWORD $registry"
  }

  stage 'build and push image'
  sh "sh build_and_push"

  stage 'run'
  sh "cd kubernetes && sh run.sh $server datav_center"
}