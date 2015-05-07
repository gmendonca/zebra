%module cpp_zhtclient

%include "std_string.i"

%{
#include "cpp_zhtclient.h"
%}


class ZHTClient {

public:
	ZHTClient();

	ZHTClient(const std::string &zhtConf, const std::string &neighborConf);
	virtual ~ZHTClient();

	int init(const std::string &zhtConf, const std::string &neighborConf);
	std::string lookup(const std::string &key);
	int insert(const std::string &key, const std::string &val);
	int compare_swap_int(const std::string &key, const std::string &seen_val,
				const std::string &new_val);
	std::string compare_swap_string(const std::string &key, const std::string &seen_val,
				const std::string &new_val);
};