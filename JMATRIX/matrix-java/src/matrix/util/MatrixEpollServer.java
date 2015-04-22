package matrix.util;

public class MatrixEpollServer {

//public:
public void	MatrixEpollServer(long port, MatrixScheduler ms) {
	_port = port;
	_ms = ms; /////////////
	_eventQueue();
	eqMutex = Mutex();
}

public void serve() {

}

/*
private:
	int create_and_bind(const char*);
	int create_and_bind(const char*, const char*);
	int make_socket_non_blocking(const int&);
	int make_svr_socket();
	int reuse_sock(int);
	void init_thread();

private:
	static void* threaded_serve(void*);

private:
	MatrixEpollServer();

private:
	MatrixScheduler *_ms;
	long _port;
	Mutex eqMutex;
	queue<MatrixEventData> _eventQueue;

private:
	static const int MAX_EVENTS;
*/
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