%module cpp_zhtclient
%{
#include "cpp_zhtclient.h"
%}

class ZHTClient {

public:
	ZHTClient();

	ZHTClient(const string &zhtConf, const string &neighborConf);
	virtual ~ZHTClient();

	int init(const string &zhtConf, const string &neighborConf);
	string lookup(const string &key);
	int insert(const string &key, const string &val);
	int compare_swap_int(const string &key, const string &seen_val,
				const string &new_val);
	string compare_swap_string(const string &key, const string &seen_val,
				const string &new_val);
};