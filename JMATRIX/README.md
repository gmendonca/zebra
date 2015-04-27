# JMATRIX: a Java MAny-Task computing execution fabRIc at eXascale

Rewriting [MATRIX](https://github.com/kwangiit/matrix_v2) code in Java. Stil in progress.

First of all, it's necessary to set up the environment.
-  Install java, google protocol buffer and set up the enviroments variables.
https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get
--  sudo apt-get update
--  sudo apt-get install openjdk-7-jdk
--  export JAVA_HOME
--  sudo apt-get install g++ gcc
--  sudo apt-get install autoconf
--  sudo apt-get install libtool
--  sudo apt-get install make
--  sudo apt-get install pkg-config
--  export LD_LIBRARY_PATH=/usr/local/lib/:$LD_LIBRARY_PATH

Install protocol buffer (watch out for spaces http://stackoverflow.com/questions/22926266/install-protocol-buffer-compiler-error-under-mac)
https://github.com/google/protobuf
https://github.com/protobuf-c/protobuf-c

git init
git clone https://github.com/google/protobuf.git 
git clone https://github.com/protobuf-c/protobuf-c.git

For each folder: (Do the protobuf first, then protobuf-c)
./autogen.sh
./configure
make && make check
sudo make install


http://stackoverflow.com/questions/11697572/protobuf-java-code-has-build-errors
protoc --java_out=src/main/java -I../src ../src/google/protobuf/descriptor.proto

https://developers.google.com/protocol-buffers/docs/cpptutorial
protoc -I=$SRC_DIR --cpp_out=$DST_DIR $SRC_DIR/addressbook.proto