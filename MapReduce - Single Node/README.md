# Word Count - Single Node

For further explanation check the [Hadoop Website](http://hadoop.apache.org/).

## Node Configuration

First of all, install Java in the desired Compute Node.


    $ sudo apt-get update
    $ sudo apt-get install openjdk-7-jdk


Then edit your .bashrc file to set up your JAVA_HOME environment variable.

[Download Hadoop](http://hadoop.apache.org/releases.html#Download) and send to your node.

You can extract the Hadoop files and set in the /usr/local/hadoop directory.

Edit your .bashrc file and add the following lines:

    export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre
    export PATH=$JAVA_HOME/bin:/usr/local/hadoop/bin:$PATH
    export HADOOP_CLASSPATH=/usr/lib/jvm/java-7-openjdk-amd64/lib/tools.jar

After editing:

    $ source .bashrc

Then edit the file etc/hadoop/hadoop-env.sh in the Hadoop directory to add the following line:

    export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre
    export HADOOP_PREFIX=/usr/local/hadoop

This is the default Standalone installation of Hadoop.

## MapReduce v1.0

Create a [WordCount.java](https://github.com/gmendonca/zebra/blob/master/MapReduce%20-%20Single%20Node/WordCount.java) and compile it, and create a jar:

    $ bin/hadoop com.sun.tools.javac.Main WordCount.java
    $ jar cf wc.jar WordCount*.class

Create a input directory in top of HDFS:

    $ hadoop fs –mkdir /user/mylovelyuser/input/

You may create inputs files or copy into this directory. After that, you may run the application:

    $ bin/hadoop jar wc.jar WordCount input/ output/

You will be able to see the output after this:

    $ bin/hdfs dfs -cat /user/mylovelyuser/output/part-r-00000

## MapReduce v2.0

For run The 2.0 version we need a different type of node configuration. This needs the HDFS to be up and running, especially for the DistributedCache-related features. Hence it only works with a pseudo-distributed or fully-distributed Hadoop installation.

For the pseudo-distributed installation, edit both of files above:

etc/hadoop/core-site.xml:

```xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

etc/hadoop/hdfs-site.xml:

```xml
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
```

Now check that you can ssh to the localhost without a passphrase:

    $ ssh localhost

If you cannot ssh to localhost without a passphrase, execute the following commands:

    $ ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
    $ cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys

After all this steps described in the Hadoop Website, just format the filesystems, start the daemons for Namenode and Data Node, and create the directory for the MapReduce jobs:

    $ bin/hdfs namenode -format
    $ sbin/start-dfs.sh
    $ bin/hdfs dfs -mkdir /user
    $ bin/hdfs dfs -mkdir /user/mylovelyuser

By this point, you can run the v1.0 example as well.
Just copy your input files to the directory created for the MapReduce jobs:

    $ bin/hdfs dfs -put input/ /user/mylovelyuser/input

You can check your files with:

    $ bin/hdfs dfs -ls /user/mylovelyuser/input

Now, just run your program:

    $ bin/hadoop jar wc.jar WordCount /user/mylovelyuser/input /user/mylovelyuser/output

And check you solution in (to get the file use -get):

    $ bin/hdfs dfs -cat /user/mylovelyuser/output/*

For finish the daemos just run:

    $ sbin/stop-dfs.sh

For the YARN(MapReduce 2.0) part it's necessary to edit two more files:

etc/hadoop/mapred-site.xml:

```xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

etc/hadoop/yarn-site.xml:

```xml
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
       <name>yarn.application.classpath</name>
       <value>
            /usr/local/hadoop/etc/hadoop,
            /usr/local/hadoop/share/hadoop/common/*,
            /usr/local/hadoop/share/hadoop/common/lib/*,
            /usr/local/hadoop/share/hadoop//hdfs/*,
            /usr/local/hadoop/share/hadoop/hdfs/lib/*,
            /usr/local/hadoop/share/hadoop/mapreduce/*,
            /usr/local/hadoop/share/hadoop/mapreduce/lib/*,
            /usr/local/hadoop/share/hadoop/yarn/*,
            /usr/local/hadoop/share/hadoop/yarn/lib/*
       </value>
   </property>
</configuration>
```

Start ResourceManager daemon and NodeManager daemon:

    $ sbin/start-yarn.sh

Create a [WordCount2.java](https://github.com/gmendonca/zebra/blob/master/MapReduce%20-%20Single%20Node/WordCount2.java) and compile it, and create a jar:

    $ bin/hadoop com.sun.tools.javac.Main WordCount2.java
    $ jar cf wc2.jar WordCount2*.class

Then create and copy the input, run the MapReduce job and check the result:

    $ bin/hdfs dfs -put input2/ /user/mylovelyuser/input2

    $ bin/hadoop jar wc2.jar WordCount2 /user/mylovelyuser/input2 /user/mylovelyuser/output2

    $ bin/hdfs dfs -cat /user/mylovelyuser/output2/*
