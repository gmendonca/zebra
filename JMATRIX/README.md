# JMATRIX: a Java MAny-Task computing execution fabRIc at eXascale

Rewriting [MATRIX](https://github.com/kwangiit/matrix_v2) code in Java. Stil in progress.

First of all, it's necessary to set up the environment.

Install java, google protocol buffer and set up the enviroments variables.
([Good Tutorial for Java](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get))

```sh
  sudo apt-get update
  sudo apt-get install openjdk-7-jdk
  sudo apt-get install g++ gcc
  sudo apt-get install autoconf
  sudo apt-get install libtool
  sudo apt-get install make
  sudo apt-get install pkg-config
  sudo apt-get install unzip
  sudo apt-get install git
  #JAVA_HOME
  echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre/bin/java" >> ~/.bashrc
  #LD_LIBRARY_PATH
  echo "export LD_LIBRARY_PATH=$HOME/matrix-java/matrix/zht/:/usr/local/lib/:$LD_LIBRARY_PATH" >> ~/.bashrc
  source ~/.bashrc
```

Install protocol buffer ([Watch out for spaces](http://stackoverflow.com/questions/22926266/install-protocol-buffer-compiler-error-under-mac))
* https://github.com/google/protobuf
* https://github.com/protobuf-c/protobuf-c

```sh
git init
git clone https://github.com/google/protobuf.git 
git clone https://github.com/protobuf-c/protobuf-c.git
```

For each folder: (Do the protobuf first, then protobuf-c)
```sh
./autogen.sh
./configure
make && make check
sudo make install
```
Then install JMATRIX:
```sh
git clone https://github.com/gmendonca/zebra.git
```

If the JMATRIX code is not working due to google protocol buffer, it's due to this [error](http://stackoverflow.com/questions/11697572/protobuf-java-code-has-build-errors), so just run the command below:

```sh
protoc --java_out=src/main/java -I../src ../src/google/protobuf/descriptor.proto
```

Then enter the ZHT [directory](https://github.com/gmendonca/zebra/tree/master/JMATRIX/matrix-java/src/matrix/zht) (matrix-java/matrix/zht) and compile it. If your protocol buffer bindings is a newer version, you might need to redo it, using the following [commands](https://developers.google.com/protocol-buffers/docs/cpptutorial):

* Example: protoc -I=$SRC_DIR --cpp_out=$DST_DIR $SRC_DIR/addressbook.proto

```sh
protoc -I=. --cpp_out=. ./*.proto
protoc-c -I=. --c_out=. ./*.proto
```

Install [SWIG](http://www.swig.org/Doc1.3/SWIGPlus.html):
```sh
sudo apt-get install swig
```

Running SWIG for ZHT Wrapping:

```sh
swig -c++ -java -package "matrix.zht" cpp_zhtclient.i
g++ -c -fPIC -lprotobuf -lprotobuf-c -I/usr/lib/jvm/java-7-openjdk-amd64/include/ cpp_zhtclient_wrap.cxx
# For SWIG you need to compile the code as well, but is already when compiling ZHT
# g++ -c -fPIC cpp_zhtclient.cpp -o cpp_zhtclient.o
g++ -shared cpp_zhtclient.o cpp_zhtclient_wrap.o /usr/local/lib/libprotobuf.so  ZHTServer.o lock_guard.o meta.pb-c.o lru_cache.o meta.pb.o zpack.pb.o novoht.o bigdata_transfer.o Const.o ConfHandler.o ConfEntry.o ProxyStubFactory.o proxy_stub.o ip_proxy_stub.o mq_proxy_stub.o ipc_plus.o tcp_proxy_stub.o udp_proxy_stub.o ZHTUtil.o Env.o Util.o StrTokenizer.o EpollServer.o ZProcessor.o ip_server.o HTWorker.o TSafeQueue.o -o libcpp_zhtclient.so
```

This project uses SWIG that runs on top of JNI, for JNI refer to [this](http://www.ibm.com/developerworks/java/tutorials/j-jni/j-jni.html) and [this](http://docs.oracle.com/javase/6/docs/technotes/guides/jni/spec/jniTOC.html).

Example creating library:
```sh
g++ -I /usr/lib/jvm/java-7-openjdk-amd64/include/ matrixzhtclient.cpp -o matrixzhtclient.so
```


You might need to add this too:

```sh
sudo echo "ubuntu	soft	nofile	70000" >> /etc/security/limits.conf
sudo echo "ubuntu	hard	nofile	70000" >> /etc/security/limits.conf
sudo echo "fs.file-max = 70000" >> /etc/sysctl.conf
```