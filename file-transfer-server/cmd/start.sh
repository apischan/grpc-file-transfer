#!/usr/bin/env bash

PYTHONPATH=$PYTHONPATH:../generated/*
echo $PYTHONPATH
/home/apischan/dev/workspaces/grpc-file-transfer/venv/bin/python /home/apischan/dev/workspaces/grpc-file-transfer/file-transfer-server/src/file_server.py