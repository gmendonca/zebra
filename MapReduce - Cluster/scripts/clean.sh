#!/bin/bash
echo "cleaning"
./cleaner.sh
echo "formating namenode"
hadoop namenode -format
