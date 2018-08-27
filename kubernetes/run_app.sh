#!/usr/bin/env bash
if [ $# -eq 2 ]; then
    server="$1"
    app_name="$2"
    kubectl="kubectl -s $server"
    if [ "$($kubectl get rc -l name=${app_name}|grep ${app_name}|awk '{{ print $1 }}')" != "" ]; then
        $kubectl delete -f ${app_name}-rc.yml
    fi
    sleep 5
    if [ "$($kubectl get svc -l name=${app_name}|grep ${app_name}|awk '{{ print $1 }}')" = "" ]; then
        $kubectl create -f ${app_name}-svc.yml
    fi
    $kubectl create -f ${app_name}-rc.yml
fi