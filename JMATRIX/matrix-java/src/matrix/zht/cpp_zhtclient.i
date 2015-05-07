%module cpp_zhtclient

%include "std_string.i"

%{
#include "cpp_zhtclient.h"
%}

class ZHTClient {

public:
	ZHTClient();

	ZHTClient(const string &zhtConf, const string &neighborConf);
	virtual ~ZHTClient();

	int init(const string &zhtConf, const string &neighborConf);
	int init(const char *zhtConf, const char *neighborConf);
	int lookup(const string &key, string &result);
	int lookup(const char *key, char *result);
	string lookup(const string &key);
	int remove(const string &key);
	int remove(const char *key);
	int insert(const string &key, const string &val);
	int insert(const char *key, const char *val);
	int append(const string &key, const string &val);
	int append(const char *key, const char *val);
	int compare_swap(const string &key, const string &seen_val,
			const string &new_val, string &result);
	int compare_swap(const char *key, const char *seen_val, const char *new_val,
			char *result);
	int compare_swap_int(const string &key, const string &seen_val,
				const string &new_val);
	string compare_swap_string(const string &key, const string &seen_val,
				const string &new_val);
	int state_change_callback(const string &key, const string &expected_val,
			int lease);
	int state_change_callback(const char *key, const char *expeded_val,
			int lease);
	int teardown();


	int commonOp(const string &opcode, const string &key, const string &val,
			const string &val2, string &result, int lease);
	string commonOpInternal(const string &opcode, const string &key,
			const string &val, const string &val2, string &result, int lease);
	string extract_value(const string &returnStr);

private:
	ProtoProxy *_proxy;
	int _msg_maxsize;
};