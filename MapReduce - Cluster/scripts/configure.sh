#!/bin/bash
echo $0
echo $1
echo $2
echo "updating apt-get"
sudo apt-get update > /dev/null
echo "installing emacs"
#sudo apt-get install emacs
echo "installing jdk"
sudo apt-get install --yes default-jdk > /dev/null 2>&1
echo "deleting first line from hosts"
sudo sed -i '1d' /etc/hosts
echo "adding hostname"
echo "$1 $2" | sudo tee -a /etc/hosts
sudo hostname $2
echo "editing .bashrc"
echo "export HADOOP_CONF=/home/ubuntu/hadoop/conf" | sudo tee -a /home/ubuntu/.bashrc
echo "export HADOOP_PREFIX=/home/ubuntu/hadoop" | sudo tee -a /home/ubuntu/.bashrc
echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" | sudo tee -a /home/ubuntu/.bashrc
echo 'export PATH=$PATH:$HADOOP_PREFIX/bin' | sudo tee -a /home/ubuntu/.bashrc
source ~/.bashrc
echo "$HADOOP_PREFIX"
echo "downloading hadoop"
wget http://apache.mirror.gtcomm.net/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gz > /dev/null
tar -xzf hadoop-1.2.1.tar.gz
mv hadoop-1.2.1 hadoop
#echo get data set
mkdir hdfstmp
echo "editing env.sh"
echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" | sudo tee -a /home/ubuntu/hadoop/conf/hadoop-env.sh

echo "editing core-site.xml"
sudo sed -i '/configuration/d' /home/ubuntu/hadoop/conf/core-site.xml
echo "<configuration><property><name>fs.default.name</name><value>hdfs://ec2-54-191-56-66.us-west-2.compute.amazonaws.com:8020</value></property><property><name>hadoop.tmp.dir</name><value>/home/ubuntu/hdfstmp</value></property></configuration>" | sudo tee -a /home/ubuntu/hadoop/conf/core-site.xml

echo "editing hdfs-site.xml"
sudo sed -i '/configuration/d' /home/ubuntu/hadoop/conf/hdfs-site.xml
echo "<configuration><property><name>dfs.replication</name><value>15</value></property><property><name>dfs.permissions</name><value>false</value></property></configuration>" | sudo tee -a /home/ubuntu/hadoop/conf/hdfs-site.xml


echo "editing mapred.xml"
sudo sed -i '/configuration/d' /home/ubuntu/hadoop/conf/mapred-site.xml
echo "<configuration><property><name>mapred.job.tracker</name><value>hdfs://ec2-54-191-56-66.us-west-2.compute.amazonaws.com:8021</value></property></configuration>" | sudo tee -a /home/ubuntu/hadoop/conf/mapred-site.xml

sudo sed -i '/localhost/d' /home/ubuntu/hadoop/conf/masters
sudo sed -i '/localhost/d' /home/ubuntu/hadoop/conf/slaves
echo "$2" | sudo tee -a /home/ubuntu/hadoop/conf/slaves