#include "../matrix_client_ZHTClient.h"

#include "cpp_zhtclient.h"

#include <stdlib.h>
#include <string.h>

//#include "zpack.pb.h"
#include "ZHTUtil.h"
#include "ConfHandler.h"
#include "Env.h"
#include "StrTokenizer.h"

using namespace iit::datasys::zht::dm;

JNIEXPORT jstring JNICALL Java_matrix_client_ZHTClient_lookUp(JNIEnv *env, jobject obj, jstring key) {
	string val;
	string val2;
	string result;

	const char *ckey = env->GetStringUTFChars(key, 0);
	string skey(ckey);

	int rc = commonOp(Const::ZSC_OPC_LOOKUP, skey, val, val2, result, 1);

	result = extract_value(result);

	const char *cstr = result.c_str();
	env->ReleaseStringUTFChars(key, ckey);
	return env->NewStringUTF(cstr);
}

JNIEXPORT jint JNICALL Java_matrix_client_ZHTClient_insertZHT(JNIEnv *env, jobject obj, jstring key, jstring val) {
	string val2;
	string& result;


	const char *ckey = env->GetStringUTFChars(key, 0);
	string skey(ckey);
	const char *cval = env->GetStringUTFChars(val, 0);
	string sval(cval);

	int rc = ZHTClient::commonOp(Const::ZSC_OPC_INSERT, skey, sval, val2, result, 1);

	return rc;
}

JNIEXPORT jint JNICALL Java_matrix_client_ZHTClient_compareSwapInt(JNIEnv *env, jobject obj, jstring key, jstring seen_val, jstring new_val) {

	string& result;

	const char *ckey = env->GetStringUTFChars(key, 0);
	string skey(ckey);
	const char *cseen_val = env->GetStringUTFChars(seen_val, 0);
	string sseen_val(cseen_val);
	const char *cnew_val = env->GetStringUTFChars(new_val, 0);
	string snew_val(cnew_val);

	int rc = ZHTClient::commonOp(Const::ZSC_OPC_CMPSWP, skey, sseen_val, snew_val, result, 1);

	return rc;
}

JNIEXPORT jstring JNICALL Java_matrix_client_ZHTClient_compareSwapString(JNIEnv *env, jobject obj, jstring key, jstring seen_val, jstring new_val) {

	string& result;

	const char *ckey = env->GetStringUTFChars(key, 0);
	string skey(ckey);
	const char *cseen_val = env->GetStringUTFChars(seen_val, 0);
	string sseen_val(cseen_val);
	const char *cnew_val = env->GetStringUTFChars(new_val, 0);
	string snew_val(cnew_val);

	int rc = ZHTClient::commonOp(Const::ZSC_OPC_CMPSWP, skey, sseen_val, snew_val, result, 1);

	result = ZHTClient::extract_value(result);

	return result;

}
