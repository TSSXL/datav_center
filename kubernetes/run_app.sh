#!/usr/bin/env bash
if [ $# -eq 2 ]; then
    server="$1"
    app_name="$2"
    kubectl="kubectl -s $server"
    if [ "$($kubectl get rc -l name=${app_name}|grep ${app_name}|awk '{{ print $1 }}')" != "" ]; then
        $kubectl delete -f smart-cityos-datav-center-rc.yml
    fi
    sleep 5
    if [ "$($kubectl get svc -l name=${app_name}|grep ${app_name}|awk '{{ print $1 }}')" = "" ]; then
        $kubectl create -f smart-cityos-datav-center-svc.yml
    fi
    $kubectl create -f smart-cityos-datav-center-rc.yml
fi
