package matrix.util;
/*
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <queue>
#include "scheduler_stub.h"




#include "util.h"
#include "matrix_epoll_server.h"
#include <stdlib.h>
#include <sys/epoll.h>
#include <fcntl.h>
#include <netdb.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>
*/

public class MatrixEpollServer {

}

class MatrixEventData {
//public:
public void MatrixEventData(int fd, const String buf, int bufsize,
		sockaddr addr) {
	_fd = fd;
	_buf = buf;

	_bufsize = bufsize;
	_fromaddr = addr;
}

public int fd()  {
	return _fd;
}

public String buf()  {
	return _buf;
}

public int bufsize()  {
	return _bufsize;
}

public sockaddr fromaddr() {
	return _fromaddr;
}


//private:
	int _fd;
	String _buf;
	int _bufsize;
	sockaddr _fromaddr;
}

class MatrixEpollData {
//public:
	public void MatrixEpollData(const int& fd, const sockaddr * const sender) {
		_fd = fd;
		_sender = sender; //deep copy

	}


public int fd()  {
	return _fd;
}

public  sockaddr sender()  {
	return _sender;
}

//private:
	int _fd;
	sockaddr _sender; //const *
}