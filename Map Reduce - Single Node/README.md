# Word Count - Single Node

For further explanation check the [Hadoop Website](http://hadoop.apache.org/).

## Node Configuration

First of all, install Java in the desired Compute Node.

'''
sudo apt-get update
sudo apt-get install openjdk-7-jre
'''

Then edit your .bashrc file to set up your JAVA_HOME environment variable.

[Download Hadoop](http://hadoop.apache.org/releases.html#Download) and send to your node.

You can extract the hadoop files and set in the /usr/local/hadoop directory.

The edit the file etc/hadoop/hadoop-env.sh to add the following line:

    export HADOOP_PREFIX=/usr/local/hadoop
