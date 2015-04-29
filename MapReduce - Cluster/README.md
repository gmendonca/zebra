# Word Count - Cluster

For further explanation check the [Hadoop Website](http://hadoop.apache.org/).

[Resources](https://github.com/yadudoc/cloud-tutorials/)

```
sudo apt-get update
sudo apt-get install openjdk-7-jdk
sudo apt-get install python python-libcloud

wget http://mirrors.gigenet.com/apache/hadoop/common/hadoop-2.7.0/hadoop-2.7.0.tar.gz
tar xvf hadoop-2.7.0.tar.gz
sudo mv hadoop-2.7.0/ /usr/local/hadoop/

echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre" >> .bashrc
echo "export PATH=$JAVA_HOME/bin:/usr/local/hadoop/bin:$PATH" >> .bashrc
echo "export HADOOP_CLASSPATH=/usr/lib/jvm/java-7-openjdk-amd64/lib/tools.jar" >> .bashrc
echo "export HADOOP_PREFIX=/usr/local/hadoop" >> .bashrc
source .bashrc

echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre
export HADOOP_PREFIX=/usr/local/hadoop" >> /usr/local/hadoop/etc/hadoop/hadoop-env.sh
echo "export HADOOP_PREFIX=/usr/local/hadoop" >> /usr/local/hadoop/etc/hadoop/hadoop-env.sh

scp -i ~/.ssh/hadoop-cluster.pem ~/.ssh/hadoop-cluster.pem ubuntu@52.10.64.249:.
scp -i ~/.ssh/hadoop-cluster.pem credentials.csv ubuntu@52.10.64.249:.

mv hadoop-cluster.pem .ssh/
mkdir .credentials/
mv credentials.csv .credentials/
```
