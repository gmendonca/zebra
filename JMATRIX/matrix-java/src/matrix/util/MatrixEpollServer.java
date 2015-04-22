package matrix.util;

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