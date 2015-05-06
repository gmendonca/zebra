# JMATRIX: a Java MAny-Task computing execution fabRIc at eXascale

Rewriting [MATRIX](https://github.com/kwangiit/matrix_v2) code in Java. Stil in progress.

First of all, it's necessary to set up the environment.

Install java, google protocol buffer and set up the enviroments variables.
([Good Tutorial for Java](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get))

```sh
  sudo apt-get update
  sudo apt-get install openjdk-7-jdk
  export JAVA_HOME
  sudo apt-get install g++ gcc
  sudo apt-get install autoconf
  sudo apt-get install libtool
  sudo apt-get install make
  sudo apt-get install pkg-config
  sudo apt-get install unzip
  sudo apt-get install git
  sudo apt-get install SWIG
  export LD_LIBRARY_PATH=/usr/local/lib/:$LD_LIBRARY_PATH
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


http://stackoverflow.com/questions/11697572/protobuf-java-code-has-build-errors
protoc --java_out=src/main/java -I../src ../src/google/protobuf/descriptor.proto

https://developers.google.com/protocol-buffers/docs/cpptutorial
protoc -I=$SRC_DIR --cpp_out=$DST_DIR $SRC_DIR/addressbook.proto

```sh
protoc -I=. --cpp_out=. ./*.proto
protoc-c -I=. --c_out=. ./*.proto
```

for jni refer to this http://www.ibm.com/developerworks/java/tutorials/j-jni/j-jni.html
http://docs.oracle.com/javase/6/docs/technotes/guides/jni/spec/jniTOC.html
creating library
```sh
g++ -I /usr/lib/jvm/java-7-openjdk-amd64/include/ matrixzhtclient.cpp -o matrixzhtclient.so
```


http://www.swig.org/Doc1.3/SWIGPlus.html
```sh
sudo apt-get install swig
```

Running SWIG for ZHT Wrapping:

```sh
swig -c++ -java -package "matrix.zht" cpp_zhtclient.i
g++ -c -fPIC -lprotobuf -lprotobuf-c -I/usr/lib/jvm/java-7-openjdk-amd64/include/ cpp_zhtclient_wrap.cxx
# For SWIG you need to compile the code as well, but is already when compiling ZHT
# g++ -c -fPIC cpp_zhtclient.cpp -o cpp_zhtclient.o
g++ -shared cpp_zhtclient.o cpp_zhtclient_wrap.o Const.o /usr/local/lib/libprotobuf.so -o libcpp_zhtclient.so
```

