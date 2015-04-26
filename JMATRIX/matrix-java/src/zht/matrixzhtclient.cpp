#include "../matrix_client_ZHTClient.h"

#include "cpp_zhtclient.h"

#include  <stdlib.h>
#include <string.h>

//#include "zpack.pb.h"
#include "ZHTUtil.h"
#include "ConfHandler.h"
#include "Env.h"
#include "StrTokenizer.h"

using namespace iit::datasys::zht::dm;

JNIEXPORT jstring JNICALL Java_matrix_client_ZHTClient_lookUp(JNIEnv *, jobject, jstring) {
	string val;
	string val2;
	int rc = commonOp(Const::ZSC_OPC_LOOKUP, key, val, val2, result, 1);
	result = extract_value(result);

	return rc;
}

JNIEXPORT jint JNICALL Java_matrix_client_ZHTClient_insertZHT(JNIEnv *, jobject, jstring, jstring) {
	string skey(key);
	string sresult;

	int rc = lookup(skey, sresult);

	strncpy(result, sresult.c_str(), sresult.size() + 1);

	return rc;
}

JNIEXPORT jint JNICALL Java_matrix_client_ZHTClient_compareSwap(JNIEnv *, jobject, jstring, jstring, jstring, jstring) {
	string skey(key);
	string sresult;

	int rc = lookup(skey, sresult);

	strncpy(result, sresult.c_str(), sresult.size() + 1);

	return rc;
}
