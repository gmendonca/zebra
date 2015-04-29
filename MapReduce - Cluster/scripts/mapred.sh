#!/bin/bash
echo "copying dataset"
hadoop dfs -mkdir input
hadoop dfs -put dataset input
echo "running mapred"
hadoop jar wordcount.jar org.cs554.WordCount input output
