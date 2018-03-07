#include <jni.h>
#include <stdlib.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif


#include "sddapi.h"


//Some additional methods from the sdd library that are included in the Java interface
    
void add_var_before_lca(int count, SddLiteral* variables, SddManager* manager);
void add_var_after_lca(int count, SddLiteral* variables, SddManager* manager);
void move_var_before_first(SddLiteral var, SddManager* manager);
void move_var_after_last(SddLiteral var, SddManager* manager);
void move_var_before(SddLiteral var, SddLiteral target_var, SddManager* manager);
void move_var_after(SddLiteral var, SddLiteral target_var, SddManager* manager);
void remove_var_added_last(SddManager* manager);


JNIEXPORT jstring JNICALL Java_jni_SddLibJNI_PRIsS_1get(JNIEnv *jenv, jclass jcls) {
  jstring jresult = 0 ;
  char *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (char *)("zu");
  if (result) jresult = (*jenv)->NewStringUTF(jenv, (const char *)result);
  return jresult;
}


JNIEXPORT jstring JNICALL Java_jni_SddLibJNI_PRInsS_1get(JNIEnv *jenv, jclass jcls) {
  jstring jresult = 0 ;
  char *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (char *)("u");
  if (result) jresult = (*jenv)->NewStringUTF(jenv, (const char *)result);
  return jresult;
}


JNIEXPORT jstring JNICALL Java_jni_SddLibJNI_PRIrcS_1get(JNIEnv *jenv, jclass jcls) {
  jstring jresult = 0 ;
  char *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (char *)("u");
  if (result) jresult = (*jenv)->NewStringUTF(jenv, (const char *)result);
  return jresult;
}


JNIEXPORT jstring JNICALL Java_jni_SddLibJNI_PRImcS_1get(JNIEnv *jenv, jclass jcls) {
  jstring jresult = 0 ;
  char *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (char *)("llu");
  if (result) jresult = (*jenv)->NewStringUTF(jenv, (const char *)result);
  return jresult;
}


JNIEXPORT jstring JNICALL Java_jni_SddLibJNI_PRIlitS_1get(JNIEnv *jenv, jclass jcls) {
  jstring jresult = 0 ;
  char *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (char *)("ld");
  if (result) jresult = (*jenv)->NewStringUTF(jenv, (const char *)result);
  return jresult;
}


JNIEXPORT jint JNICALL Java_jni_SddLibJNI_CONJOIN_1get(JNIEnv *jenv, jclass jcls) {
  jint jresult = 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  result = (int)(0);
  jresult = (jint)result; 
  return jresult;
}


JNIEXPORT jint JNICALL Java_jni_SddLibJNI_DISJOIN_1get(JNIEnv *jenv, jclass jcls) {
  jint jresult = 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  result = (int)(1);
  jresult = (jint)result; 
  return jresult;
}


//// SDD MANAGER FUNCTIONS

//SddManager* sdd_manager_new(Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1new(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (SddManager *)sdd_manager_new(arg1);
  *(SddManager **)&jresult = result; 
  return jresult;
}

//SddManager* sdd_manager_create(SddLiteral var_count, int auto_gc_and_minimize);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1create(JNIEnv *jenv, jclass jcls, jlong jarg1, jint jarg2) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  int arg2 ;
  SddManager *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = (int)jarg2; 
  result = (SddManager *)sdd_manager_create(arg1,arg2);
  *(SddManager **)&jresult = result; 
  return jresult;
}

//SddManager* sdd_manager_copy(SddSize size, SddNode** nodes, SddManager* from_manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1copy(JNIEnv *jenv, jclass jcls, jlongArray jarg1, jlong jarg2) {
  jlong jresult = 0;
  SddManager *arg2 = (SddManager *) 0 ;
  SddManager *result = 0;
  
  (void)jenv;
  (void)jcls;
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, jarg1, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv, jarg1);
  SddNode* arg1[length];
    for(int i=0; i<length; i++){
    arg1[i]=*(SddNode **)&inCArray[i];
  }
  
  arg2 = *(SddManager **)&jarg2; 
  result = sdd_manager_copy(length, arg1, arg2);
  *(SddManager **)&jresult = result;
  
  // copy the copied nodes to jarg1
  for(int i=0; i<length; i++){
    *(SddNode **)&inCArray[i] = arg1[i];
  } 
  (*jenv) ->SetLongArrayRegion(jenv, jarg1, 0, length, inCArray);
  
  return jresult;
}


//void sdd_manager_free(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1free(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_free(arg1);
}

//void sdd_manager_print(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1print(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_print(arg1);
}



//void sdd_manager_auto_gc_and_minimize_on(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1auto_1gc_1and_1minimize_1on(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_auto_gc_and_minimize_on(arg1);
}

//void sdd_manager_auto_gc_and_minimize_off(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1auto_1gc_1and_1minimize_1off(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_auto_gc_and_minimize_off(arg1);
}
//int sdd_manager_is_auto_gc_and_minimize_on(SddManager* manager);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1manager_1is_1auto_1gc_1and_1minimize_1on(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1;
  result = (int)sdd_manager_is_auto_gc_and_minimize_on(arg1);
  jresult = (jint)result; 
  return jresult;
}

// Not needed because in java a custom minimization function cannot be provided.
// //void sdd_manager_set_minimize_function(SddVtreeSearchFunc func, SddManager* manager);
// JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1minimize_1function(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
//   SddVtreeSearchFunc *arg1 = (SddVtreeSearchFunc *) 0 ;
//   SddManager *arg2 = (SddManager *) 0 ;
//   
//   (void)jenv;
//   (void)jcls;
//   arg1 = *(SddVtreeSearchFunc **)&jarg1; 
//   arg2 = *(SddManager **)&jarg2; 
//   sdd_manager_set_minimize_function(arg1,arg2);
// }
// 
// // void sdd_manager_unset_minimize_function(SddManager* manager);
// JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1unset_1minimize_1function(JNIEnv *jenv, jclass jcls, jlong jarg1) {
//   SddManager *arg1 = (SddManager *) 0 ;
//   
//   (void)jenv;
//   (void)jcls;
//   arg1 = *(SddManager **)&jarg1; 
//   sdd_manager_unset_minimize_function(arg1);
// }


// void* sdd_manager_options(SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1options(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  void *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (void *)sdd_manager_options(arg1);
  *(void **)&jresult = result; 
  return jresult;
}


// void sdd_manager_set_options(void* options, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1options(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  void *arg1 = (void *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(void **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_options(arg1,arg2);
}

// int sdd_manager_is_var_used(SddLiteral var, SddManager* manager);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1manager_1is_1var_1used(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jint jresult = 0 ;
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (int)sdd_manager_is_var_used(arg1,arg2);
  jresult = (jint)result; 
  return jresult;
}

// Vtree* sdd_manager_vtree_of_var(const SddLiteral var, const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1vtree_1of_1var(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (Vtree *)sdd_manager_vtree_of_var(arg1,(struct sdd_manager_t const *)arg2);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// Vtree* sdd_manager_lca_of_literals(int count, SddLiteral* literals, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1lca_1of_1literals(JNIEnv *jenv, jclass jcls, jint jarg1, jlongArray inJNIArray, jlong jarg3) {
  
  
  Vtree *result = 0 ;
  jlong jresult = 0 ;
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL != inCArray){
    jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
    SddSize arg2[length];
    for(int i=0; i<length; i++){
      arg2[i]=(SddSize) inCArray[i];
    }
    
    
    int arg1 ;
    SddManager *arg3 = (SddManager *) 0 ;
    
    arg1 = (int)jarg1;
    arg3 = *(SddManager **)&jarg3; 
    result = (Vtree *)sdd_manager_lca_of_literals(arg1,arg2,arg3);
    
    (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
  }
  *(Vtree **)&jresult = result; 
  return jresult;
}

// SddLiteral sdd_manager_var_count(SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1var_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (SddLiteral)sdd_manager_var_count(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// void sdd_manager_var_order(SddLiteral* var_order, SddManager *manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1var_1order(JNIEnv *jenv, jclass jcls, jlongArray inJNIArray, jlong mgr) {
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddSize arg1[length];
  for(int i=0; i<length; i++){
    arg1[i]=(SddSize) inCArray[i];
  }
  
  SddManager *arg2 = (SddManager *) 0 ;
  arg2 = *(SddManager **)&mgr; 
  sdd_manager_var_order(arg1,arg2);
  
  
  for(int i=0; i<length; i++){
    inCArray[i]=(jlong) arg1[i];
  }
  
  (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
}

// void sdd_manager_add_var_before_first(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1add_1var_1before_1first(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_add_var_before_first(arg1);
}

// void sdd_manager_add_var_after_last(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1add_1var_1after_1last(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_add_var_after_last(arg1);
}

// void sdd_manager_add_var_before(SddLiteral target_var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1add_1var_1before(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_add_var_before(arg1,arg2);
}

// void sdd_manager_add_var_after(SddLiteral target_var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1add_1var_1after(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_add_var_after(arg1,arg2);
}


// TERMINAL SDDS

// SddNode* sdd_manager_true(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1true(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (SddNode *)sdd_manager_true((struct sdd_manager_t const *)arg1);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_manager_false(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1false(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (SddNode *)sdd_manager_false((struct sdd_manager_t const *)arg1);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_manager_literal(const SddLiteral literal, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1literal(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_manager_literal(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}


// SDD QUERIES AND TRANSFORMATIONS

// SddNode* sdd_apply(SddNode* node1, SddNode* node2, BoolOp op, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1apply(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jint jarg3, jlong jarg4) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddNode *arg2 = (SddNode *) 0 ;
  BoolOp arg3 ;
  SddManager *arg4 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = (BoolOp)jarg3; 
  arg4 = *(SddManager **)&jarg4; 
  result = (SddNode *)sdd_apply(arg1,arg2,arg3,arg4);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_conjoin(SddNode* node1, SddNode* node2, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1conjoin(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddNode *arg2 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_conjoin(arg1,arg2,arg3);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_disjoin(SddNode* node1, SddNode* node2, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1disjoin(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddNode *arg2 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_disjoin(arg1,arg2,arg3);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_negate(SddNode* node, SddManager* manager);

JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1negate(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_negate(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_condition(SddLiteral lit, SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1condition(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  SddNode *arg2 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_condition(arg1,arg2,arg3);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_exists(SddLiteral var, SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1exists(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  SddNode *arg2 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_exists(arg1,arg2,arg3);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_exists_multiple(int* exists_map, SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1exists_1multiple(JNIEnv *jenv, jclass jcls, jintArray jExists_map, jlong jNode, jlong jManager) {
  jint *inCArray = (*jenv)->GetIntArrayElements(jenv, jExists_map, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,jExists_map);
  int cExists_map[length];
  for(int i=0; i<length; i++){
    cExists_map[i]=(int) inCArray[i];
  }

  jlong jresult = 0 ;
  SddNode *cNode = (SddNode *) 0 ;
  SddManager *cManager = (SddManager *) 0 ;
  SddNode *result = 0 ;
  

  (*jenv)->ReleaseIntArrayElements(jenv, jExists_map, inCArray, 0);
  (void)jenv;
  (void)jcls;
  cNode = *(SddNode **)&jNode; 
  cManager = *(SddManager **)&jManager; 
  result = (SddNode *)sdd_exists_multiple(cExists_map,cNode,cManager);
  *(SddNode **)&jresult = result; 
  return jresult;
}


// SddNode* sdd_exists_multiple_static(int* exists_map, SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1exists_1multiple_1static(JNIEnv *jenv, jclass jcls, jintArray jExists_map, jlong jNode, jlong jManager) {
  jint *inCArray = (*jenv)->GetIntArrayElements(jenv, jExists_map, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,jExists_map);
  int cExists_map[length];
  for(int i=0; i<length; i++){
    cExists_map[i]=(int) inCArray[i];
  }

  jlong jresult = 0 ;
  SddNode *cNode = (SddNode *) 0 ;
  SddManager *cManager = (SddManager *) 0 ;
  SddNode *result = 0 ;
  

  (*jenv)->ReleaseIntArrayElements(jenv, jExists_map, inCArray, 0);
  (void)jenv;
  (void)jcls;
  cNode = *(SddNode **)&jNode; 
  cManager = *(SddManager **)&jManager; 
  result = (SddNode *)sdd_exists_multiple_static(cExists_map,cNode,cManager);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_forall(SddLiteral var, SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1forall(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  SddNode *arg2 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_forall(arg1,arg2,arg3);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_minimize_cardinality(SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1minimize_1cardinality(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_minimize_cardinality(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_global_minimize_cardinality(SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1global_1minimize_1cardinality(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_global_minimize_cardinality(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddLiteral sdd_minimum_cardinality(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1minimum_1cardinality(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (SddLiteral)sdd_minimum_cardinality(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddModelCount sdd_model_count(SddNode* node, SddManager* manager);
JNIEXPORT jobject JNICALL Java_jni_SddLibJNI_sdd_1model_1count(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jobject jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddModelCount result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddModelCount)sdd_model_count(arg1,arg2);
  {
    jbyteArray ba = (*jenv)->NewByteArray(jenv, 9);
    jbyte* bae = (*jenv)->GetByteArrayElements(jenv, ba, 0);
    jclass clazz = (*jenv)->FindClass(jenv, "java/math/BigInteger");
    jmethodID mid = (*jenv)->GetMethodID(jenv, clazz, "<init>", "([B)V");
    jobject bigint;
    int i;
    
    bae[0] = 0;
    for(i=1; i<9; i++ ) {
      bae[i] = (jbyte)(result>>8*(8-i));
    }
    
    (*jenv)->ReleaseByteArrayElements(jenv, ba, bae, 0);
    bigint = (*jenv)->NewObject(jenv, clazz, mid, ba);
    jresult = bigint;
  }
  return jresult;
}

// SddModelCount sdd_global_model_count(SddNode* node, SddManager* manager);
JNIEXPORT jobject JNICALL Java_jni_SddLibJNI_sdd_1global_1model_1count(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jobject jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddModelCount result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddModelCount)sdd_global_model_count(arg1,arg2);
  {
    jbyteArray ba = (*jenv)->NewByteArray(jenv, 9);
    jbyte* bae = (*jenv)->GetByteArrayElements(jenv, ba, 0);
    jclass clazz = (*jenv)->FindClass(jenv, "java/math/BigInteger");
    jmethodID mid = (*jenv)->GetMethodID(jenv, clazz, "<init>", "([B)V");
    jobject bigint;
    int i;
    
    bae[0] = 0;
    for(i=1; i<9; i++ ) {
      bae[i] = (jbyte)(result>>8*(8-i));
    }
    
    (*jenv)->ReleaseByteArrayElements(jenv, ba, bae, 0);
    bigint = (*jenv)->NewObject(jenv, clazz, mid, ba);
    jresult = bigint;
  }
  return jresult;
}

// SDD NAVIGATION

// int sdd_node_is_true(SddNode* node);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1node_1is_1true(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (int)sdd_node_is_true(arg1);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_node_is_false(SddNode* node);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1node_1is_1false(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (int)sdd_node_is_false(arg1);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_node_is_literal(SddNode* node);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1node_1is_1literal(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (int)sdd_node_is_literal(arg1);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_node_is_decision(SddNode* node);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1node_1is_1decision(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (int)sdd_node_is_decision(arg1);
  jresult = (jint)result; 
  return jresult;
}

// SddNodeSize sdd_node_size(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1node_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddNodeSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (SddNodeSize)sdd_node_size(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddLiteral sdd_node_literal(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1node_1literal(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (SddLiteral)sdd_node_literal(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddNode** sdd_node_elements(SddNode* node);
JNIEXPORT jlongArray JNICALL Java_jni_SddLibJNI_sdd_1node_1elements(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  
  SddNode *arg1 = (SddNode *) 0 ;
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  
  SddNode **nodes = sdd_node_elements(arg1);
  
  int l = 2*sdd_node_size(arg1);
  jlong outCArray[l];
  
  for (int i=0; i<l; i++){
    *(SddNode **)&outCArray[i] = nodes[i];
  }
  
  jlongArray outJNIArray = (*jenv)->NewLongArray(jenv, l);  // allocate
  if (NULL == outJNIArray) return NULL;
  (*jenv)->SetLongArrayRegion(jenv, outJNIArray, 0 , l, outCArray);  // copy
  return outJNIArray;
}

// void sdd_node_set_bit(int bit, SddNode* node);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1node_1set_1bit(JNIEnv *jenv, jclass jcls, jint jarg1, jlong jarg2) {
  int arg1 ;
  SddNode *arg2 = (SddNode *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (int)jarg1; 
  arg2 = *(SddNode **)&jarg2; 
  sdd_node_set_bit(arg1,arg2);
}

// int sdd_node_bit(SddNode* node);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1node_1bit(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (int)sdd_node_bit(arg1);
  jresult = (jint)result; 
  return jresult;
}


// SDD FUNCTIONS

// SddSize sdd_id(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1id(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = sdd_id(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// int sdd_garbage_collected(SddNode* node, SddSize id);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1garbage_1collected(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jint jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddSize arg2 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = (SddSize)jarg2; 
  result = (int)sdd_garbage_collected(arg1,arg2);
  jresult = (jint)result; 
  return jresult;
}

// Vtree* sdd_vtree_of(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1of(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (Vtree *)sdd_vtree_of(arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_copy(SddNode* node, SddManager* dest_manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1copy(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_copy(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_rename_variables(SddNode* node, SddLiteral* variable_map, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1rename_1variables(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray inJNIArray, jlong jarg3) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddLiteral arg2[length];
  for(int i=0; i<length; i++){
    arg2[i]=(SddLiteral) inCArray[i];
  }
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg3 = *(SddManager **)&jarg3; 
  result = (SddNode *)sdd_rename_variables(arg1,arg2,arg3);
  (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// int* sdd_variables(SddNode* node, SddManager* manager);
JNIEXPORT jintArray JNICALL Java_jni_SddLibJNI_sdd_1variables(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {

  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int *outCArray = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  outCArray = (int *)sdd_variables(arg1,arg2);
  
  SddLiteral length = sdd_manager_var_count(arg2)+1;
  jbooleanArray outJNIArray = (*jenv)->NewIntArray(jenv, length);
  if (NULL == outJNIArray) return NULL;
  (*jenv)->SetIntArrayRegion(jenv, outJNIArray, 0 , length, outCArray);
  return outJNIArray;
}


// SDD FILE I/O

// SddNode* sdd_read(const char* filename, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1read(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  char *arg1 = (char *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return 0;
  }
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_read((char const *)arg1,arg2);
  *(SddNode **)&jresult = result; 
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
  return jresult;
}

// void sdd_save(const char* fname, SddNode *node);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1save(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  char *arg1 = (char *) 0 ;
  SddNode *arg2 = (SddNode *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return ;
  }
  arg2 = *(SddNode **)&jarg2; 
  sdd_save((char const *)arg1,arg2);
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
}

// void sdd_save_as_dot(const char* fname, SddNode *node);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1save_1as_1dot(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  char *arg1 = (char *) 0 ;
  SddNode *arg2 = (SddNode *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return ;
  }
  arg2 = *(SddNode **)&jarg2; 
  sdd_save_as_dot((char const *)arg1,arg2);
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
}

// void sdd_shared_save_as_dot(const char* fname, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1shared_1save_1as_1dot(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  char *arg1 = (char *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return ;
  }
  arg2 = *(SddManager **)&jarg2; 
  sdd_shared_save_as_dot((char const *)arg1,arg2);
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
}


// SDD SIZE AND NODE COUNT
//SDD

// SddSize sdd_count(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = sdd_count(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_size(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = sdd_size(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_shared_size(SddNode** nodes, SddSize count);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1shared_1size(JNIEnv *jenv, jclass jcls, jlongArray inJNIArray, jlong jarg2) {
  
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddNode* arg1[length];
  for(int i=0; i<length; i++){
    arg1[i]=*(SddNode **)&inCArray[i];
  }
  
  jlong jresult = 0 ;
  SddSize arg2 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg2 = (SddSize)jarg2; 
  result = sdd_shared_size(arg1,arg2);
  (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
  jresult = (jlong)result; 
  return jresult;
}


//SDD OF MANAGER
// SddSize sdd_manager_size(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_size((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_manager_live_size(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1live_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_live_size((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_manager_dead_size(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1dead_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_dead_size((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_manager_count(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_count((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_manager_live_count(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1live_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_live_count((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_manager_dead_count(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1dead_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = sdd_manager_dead_count((struct sdd_manager_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}


//SDD OF VTREE

// SddSize sdd_vtree_size(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_size((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_size(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_size((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_size(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1size(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_size((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_size_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1size_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_size_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_size_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1size_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_size_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_size_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1size_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_size_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_size_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1size_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_size_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_size_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1size_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_size_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_size_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1size_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_size_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_count(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_count((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_count(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_count((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_count(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_count((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_count_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1count_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_count_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_count_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1count_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_count_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_count_at(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1count_1at(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_count_at((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}


// SddSize sdd_vtree_count_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1count_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_count_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_live_count_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1live_1count_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_live_count_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddSize sdd_vtree_dead_count_above(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1dead_1count_1above(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddSize result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = sdd_vtree_dead_count_above((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}


// CREATING VTREES

// Vtree* sdd_vtree_new(SddLiteral var_count, const char* type);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1new(JNIEnv *jenv, jclass jcls, jlong jarg1, jstring jarg2) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  char *arg2 = (char *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = 0;
  if (jarg2) {
    arg2 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg2, 0);
    if (!arg2) return 0;
  }
  result = (Vtree *)sdd_vtree_new(arg1,(char const *)arg2);
  *(Vtree **)&jresult = result; 
  if (arg2) (*jenv)->ReleaseStringUTFChars(jenv, jarg2, (const char *)arg2);
  return jresult;
}

// Vtree* sdd_vtree_new_with_var_order(SddLiteral var_count, SddLiteral* var_order, const char* type);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1new_1with_1var_1order(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray inJNIArray, jstring jarg3) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  char *arg3 = (char *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1;
  arg3 = 0;
  if (jarg3) {
    arg3 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg3, 0);
    if (!arg3) return 0;
  }
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddSize arg2[length];
  for(int i=0; i<length; i++){
    arg2[i]=(SddSize) inCArray[i];
  }
  
  result = (Vtree *)sdd_vtree_new_with_var_order(arg1,arg2,(char const *)arg3);
  *(Vtree **)&jresult = result; 
  if (arg3) (*jenv)->ReleaseStringUTFChars(jenv, jarg3, (const char *)arg3);
  
  if (arg2) (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
  
  return jresult;
}

// Vtree* sdd_vtree_new_X_constrained(SddLiteral var_count, SddLiteral* is_X_var, const char* type);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1new_1X_1constrained(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray inJNIArray, jstring jarg3) {
  jlong jresult = 0 ;
  SddLiteral arg1 ;
  char *arg3 = (char *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1;
  arg3 = 0;
  if (jarg3) {
    arg3 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg3, 0);
    if (!arg3) return 0;
  }
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return 0;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddSize arg2[length];
  for(int i=0; i<length; i++){
    arg2[i]=(SddSize) inCArray[i];
  }
  
  result = (Vtree *)sdd_vtree_new_X_constrained(arg1,arg2,(char const *)arg3);
  *(Vtree **)&jresult = result; 
  if (arg3) (*jenv)->ReleaseStringUTFChars(jenv, jarg3, (const char *)arg3);
  
  if (arg2) (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);
  
  return jresult;
}

// void sdd_vtree_free(Vtree* vtree);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1free(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  Vtree *arg1 = (Vtree *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  sdd_vtree_free(arg1);
}


// VTREE FILE I/O

// void sdd_vtree_save(const char* fname, Vtree* vtree);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1save(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  char *arg1 = (char *) 0 ;
  Vtree *arg2 = (Vtree *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return ;
  }
  arg2 = *(Vtree **)&jarg2; 
  sdd_vtree_save((char const *)arg1,arg2);
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
}

// Vtree* sdd_vtree_read(const char* filename);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1read(JNIEnv *jenv, jclass jcls, jstring jarg1) {
  jlong jresult = 0 ;
  char *arg1 = (char *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return 0;
  }
  result = (Vtree *)sdd_vtree_read((char const *)arg1);
  *(Vtree **)&jresult = result; 
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
  return jresult;
}

// void sdd_vtree_save_as_dot(const char* fname, Vtree* vtree);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1save_1as_1dot(JNIEnv *jenv, jclass jcls, jstring jarg1, jlong jarg2) {
  char *arg1 = (char *) 0 ;
  Vtree *arg2 = (Vtree *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = 0;
  if (jarg1) {
    arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
    if (!arg1) return ;
  }
  arg2 = *(Vtree **)&jarg2; 
  sdd_vtree_save_as_dot((char const *)arg1,arg2);
  if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, (const char *)arg1);
}


// SDD MANAGER VTREE

// Vtree* sdd_manager_vtree(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1vtree(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (Vtree *)sdd_manager_vtree((struct sdd_manager_t const *)arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// Vtree* sdd_manager_vtree_copy(const SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1manager_1vtree_1copy(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddManager *arg1 = (SddManager *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  result = (Vtree *)sdd_manager_vtree_copy((struct sdd_manager_t const *)arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}


// VTREE NAVIGATION

// Vtree* sdd_vtree_left(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1left(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (Vtree *)sdd_vtree_left((struct vtree_t const *)arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// Vtree* sdd_vtree_right(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1right(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (Vtree *)sdd_vtree_right((struct vtree_t const *)arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// Vtree* sdd_vtree_parent(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1parent(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (Vtree *)sdd_vtree_parent((struct vtree_t const *)arg1);
  *(Vtree **)&jresult = result; 
  return jresult;
}


// VTREE FUNCTIONS

// int sdd_vtree_is_leaf(const Vtree* vtree);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1is_1leaf(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (int)sdd_vtree_is_leaf((struct vtree_t const *)arg1);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_vtree_is_sub(const Vtree* vtree1, const Vtree* vtree2);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1is_1sub(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  Vtree *arg2 = (Vtree *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(Vtree **)&jarg2; 
  result = (int)sdd_vtree_is_sub((struct vtree_t const *)arg1,(struct vtree_t const *)arg2);
  jresult = (jint)result; 
  return jresult;
}

// Vtree* sdd_vtree_lca(Vtree* vtree1, Vtree* vtree2, Vtree* root);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1lca(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  Vtree *arg2 = (Vtree *) 0 ;
  Vtree *arg3 = (Vtree *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(Vtree **)&jarg2; 
  arg3 = *(Vtree **)&jarg3; 
  result = (Vtree *)sdd_vtree_lca(arg1,arg2,arg3);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// SddLiteral sdd_vtree_var_count(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1var_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (SddLiteral)sdd_vtree_var_count((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddLiteral sdd_vtree_var(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1var(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (SddLiteral)sdd_vtree_var((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddLiteral sdd_vtree_position(const Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1position(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddLiteral result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (SddLiteral)sdd_vtree_position((struct vtree_t const *)arg1);
  jresult = (jlong)result; 
  return jresult;
}

// not needed in java, because we cannot define search functions in java
// Vtree** sdd_vtree_location(Vtree* vtree, SddManager* manager);
// JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1location(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
//   jlong jresult = 0 ;
//   Vtree *arg1 = (Vtree *) 0 ;
//   SddManager *arg2 = (SddManager *) 0 ;
//   Vtree **result = 0 ;
//   
//   (void)jenv;
//   (void)jcls;
//   arg1 = *(Vtree **)&jarg1; 
//   arg2 = *(SddManager **)&jarg2; 
//   result = (Vtree **)sdd_vtree_location(arg1,arg2);
//   *(Vtree ***)&jresult = result; 
//   return jresult;
// }


// VTREE/SDD EDIT OPERATIONS

// int sdd_vtree_rotate_left(Vtree* vtree, SddManager* manager, int limited);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1rotate_1left(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jint jarg3) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int arg3 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  arg3 = (int)jarg3; ; 
  result = (int)sdd_vtree_rotate_left(arg1,arg2,arg3);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_vtree_rotate_right(Vtree* vtree, SddManager* manager, int limited);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1rotate_1right(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jint jarg3) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int arg3 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  arg3 = (int)jarg3; ; 
  result = (int)sdd_vtree_rotate_right(arg1,arg2,arg3);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_vtree_swap(Vtree* vtree, SddManager* manager, int limited);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1swap(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2, jint jarg3) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int arg3 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  arg3 = (int)jarg3; ; 
  result = (int)sdd_vtree_swap(arg1,arg2,arg3);
  jresult = (jint)result; 
  return jresult;
}



// LIMITS FOR VTREE/SDD EDIT OPERATIONS

// void sdd_manager_init_vtree_size_limit(Vtree* vtree, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1init_1vtree_1size_1limit(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2;
  sdd_manager_init_vtree_size_limit(arg1,arg2);
}


// void sdd_manager_update_vtree_size_limit(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1update_1vtree_1size_1limit(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1;
  sdd_manager_update_vtree_size_limit(arg1);
}

// VTREE STATE

// int sdd_vtree_bit(const Vtree* vtree);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1bit(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jint jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (int)sdd_vtree_bit((struct vtree_t const *)arg1);
  jresult = (jint)result; 
  return jresult;
}

// void sdd_vtree_set_bit(int bit, Vtree* vtree);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1set_1bit(JNIEnv *jenv, jclass jcls, jint jarg1, jlong jarg2) {
  int arg1 ;
  Vtree *arg2 = (Vtree *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (int)jarg1; 
  arg2 = *(Vtree **)&jarg2; 
  sdd_vtree_set_bit(arg1,arg2);
}

// void* sdd_vtree_data(Vtree* vtree);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1data(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  void *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  result = (void *)sdd_vtree_data(arg1);
  *(void **)&jresult = result; 
  return jresult;
}

// void sdd_vtree_set_data(void* data, Vtree* vtree);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1set_1data(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  void *arg1 = (void *) 0 ;
  Vtree *arg2 = (Vtree *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(void **)&jarg1; 
  arg2 = *(Vtree **)&jarg2; 
  sdd_vtree_set_data(arg1,arg2);
}

// void* sdd_vtree_search_state(const Vtree* vtree);
// not needed in java


// void sdd_vtree_set_search_state(void* search_state, Vtree* vtree);
// not needed in java


// GARBAGE COLLECTION

// SddRefCount sdd_ref_count(SddNode* node);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1ref_1count(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddRefCount result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  result = (SddRefCount)sdd_ref_count(arg1);
  jresult = (jlong)result; 
  return jresult;
}

// SddNode* sdd_ref(SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1ref(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_ref(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// SddNode* sdd_deref(SddNode* node, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1deref(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  SddNode *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (SddNode *)sdd_deref(arg1,arg2);
  *(SddNode **)&jresult = result; 
  return jresult;
}

// added: reference multiple nodes at once. This is more efficient than calling ref multiple times through jni
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1ref_1multi(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray jarg2) {
    
  SddManager *mgr = *(SddManager **)&jarg1;
  
  jlong *refNodesPointers = (*jenv)->GetLongArrayElements(jenv, jarg2, NULL);
  if (NULL == refNodesPointers) return;
  
  jsize length = (*jenv)->GetArrayLength(jenv,jarg2);  
  SddNode* refNodes[length];  
  for(int i=0; i<length; i++){
    refNodes[i]=*(SddNode **)&refNodesPointers[i];
    
  }
  
  (void)jenv;
  (void)jcls;
  for(int i=0; i<length; i++){
    sdd_ref(refNodes[i],mgr);
  }
  
  (*jenv)->ReleaseLongArrayElements(jenv, jarg2, refNodesPointers,0);
}

// added: dereference multiple nodes at once. This is more efficient than calling deref multiple times through jni
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1deref_1multi(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray jarg2) {
    
  SddManager *mgr = *(SddManager **)&jarg1;
  
  jlong *refNodesPointers = (*jenv)->GetLongArrayElements(jenv, jarg2, NULL);
  if (NULL == refNodesPointers) return;
  
  jsize length = (*jenv)->GetArrayLength(jenv,jarg2);  
  SddNode* refNodes[length];  
  for(int i=0; i<length; i++){
    refNodes[i]=*(SddNode **)&refNodesPointers[i];
    
  }
  
  (void)jenv;
  (void)jcls;
  for(int i=0; i<length; i++){
    sdd_deref(refNodes[i],mgr);
  }
  
  (*jenv)->ReleaseLongArrayElements(jenv, jarg2, refNodesPointers,0);
}

//added: ref-gc-deref. This is an efficient way to to garbage collection while forcing to keep a bunch of nodes.
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1ref_1gc_1deref(JNIEnv *jenv, jclass jcls, jlong jarg1, jlongArray jarg2) {
    
  SddManager *mgr = *(SddManager **)&jarg1;
  
  jlong *refNodesPointers = (*jenv)->GetLongArrayElements(jenv, jarg2, NULL);
  if (NULL == refNodesPointers) return;
  
  jsize length = (*jenv)->GetArrayLength(jenv,jarg2);  
  SddNode* refNodes[length];  
  for(int i=0; i<length; i++){
    refNodes[i]=*(SddNode **)&refNodesPointers[i];
    
  }
  
  (void)jenv;
  (void)jcls;
  for(int i=0; i<length; i++){
    sdd_ref(refNodes[i],mgr);
  }
  sdd_manager_garbage_collect(mgr);
  for(int i=0; i<length; i++){
    sdd_deref(refNodes[i],mgr);
  }

  (*jenv)->ReleaseLongArrayElements(jenv, jarg2, refNodesPointers,0);
}

// void sdd_manager_garbage_collect(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1garbage_1collect(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_garbage_collect(arg1);
}

// void sdd_vtree_garbage_collect(Vtree* vtree, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1vtree_1garbage_1collect(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_vtree_garbage_collect(arg1,arg2);
}

// int sdd_manager_garbage_collect_if(float dead_node_threshold, SddManager* manager);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1manager_1garbage_1collect_1if(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  jint jresult = 0 ;
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (int)sdd_manager_garbage_collect_if(arg1,arg2);
  jresult = (jint)result; 
  return jresult;
}

// int sdd_vtree_garbage_collect_if(float dead_node_threshold, Vtree* vtree, SddManager* manager);
JNIEXPORT jint JNICALL Java_jni_SddLibJNI_sdd_1vtree_1garbage_1collect_1if(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2, jlong jarg3) {
  jint jresult = 0 ;
  float arg1 ;
  Vtree *arg2 = (Vtree *) 0 ;
  SddManager *arg3 = (SddManager *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(Vtree **)&jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (int)sdd_vtree_garbage_collect_if(arg1,arg2,arg3);
  jresult = (jint)result; 
  return jresult;
}


// MINIMIZATION

// void sdd_manager_minimize(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1minimize(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_minimize(arg1);
}

// Vtree* sdd_vtree_minimize(Vtree* vtree, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1minimize(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (Vtree *)sdd_vtree_minimize(arg1,arg2);
  *(Vtree **)&jresult = result; 
  return jresult;
}

// void sdd_manager_minimize_limited(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1minimize_1limited(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  sdd_manager_minimize_limited(arg1);
}

// Vtree* sdd_vtree_minimize_limited(Vtree* vtree, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_sdd_1vtree_1minimize_1limited(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jlong jresult = 0 ;
  Vtree *arg1 = (Vtree *) 0 ;
  SddManager *arg2 = (SddManager *) 0 ;
  Vtree *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(Vtree **)&jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  result = (Vtree *)sdd_vtree_minimize_limited(arg1,arg2);
  *(Vtree **)&jresult = result; 
  return jresult;
}


// void sdd_manager_set_vtree_search_convergence_threshold(float threshold, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1search_1convergence_1threshold(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_search_convergence_threshold(arg1,arg2);
}


// void sdd_manager_set_vtree_search_time_limit(float time_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1search_1time_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_search_time_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_fragment_time_limit(float time_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1fragment_1time_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_fragment_time_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_operation_time_limit(float time_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1operation_1time_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_operation_time_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_apply_time_limit(float time_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1apply_1time_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_apply_time_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_operation_memory_limit(float memory_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1operation_1memory_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_operation_memory_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_operation_size_limit(float size_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1operation_1size_1limit(JNIEnv *jenv, jclass jcls, jfloat jarg1, jlong jarg2) {
  float arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (float)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_operation_size_limit(arg1,arg2);
}

// void sdd_manager_set_vtree_cartesian_product_limit(SddSize size_limit, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_sdd_1manager_1set_1vtree_1cartesian_1product_1limit(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  SddSize arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddSize)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  sdd_manager_set_vtree_cartesian_product_limit(arg1,arg2);
}


// WMC

// WmcManager* wmc_manager_new(SddNode* node, int log_mode, SddManager* manager);
JNIEXPORT jlong JNICALL Java_jni_SddLibJNI_wmc_1manager_1new(JNIEnv *jenv, jclass jcls, jlong jarg1, jint jarg2, jlong jarg3) {
  jlong jresult = 0 ;
  SddNode *arg1 = (SddNode *) 0 ;
  int arg2 ;
  SddManager *arg3 = (SddManager *) 0 ;
  WmcManager *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddNode **)&jarg1; 
  arg2 = (int)jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  result = (WmcManager *)wmc_manager_new(arg1,arg2,arg3);
  *(WmcManager **)&jresult = result; 
  return jresult;
}

// void wmc_manager_free(WmcManager* wmc_manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_wmc_1manager_1free(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  WmcManager *arg1 = (WmcManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(WmcManager **)&jarg1; 
  wmc_manager_free(arg1);
}

// void wmc_set_literal_weight(const SddLiteral literal, const SddWmc weight, WmcManager* wmc_manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_wmc_1set_1literal_1weight(JNIEnv *jenv, jclass jcls, jlong jarg1, jdouble jarg2, jlong jarg3) {
  SddLiteral arg1 ;
  SddWmc arg2 ;
  WmcManager *arg3 = (WmcManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = (SddWmc)jarg2; 
  arg3 = *(WmcManager **)&jarg3; 
  wmc_set_literal_weight(arg1,arg2,arg3);
}

// SddWmc wmc_propagate(WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1propagate(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jdouble jresult = 0 ;
  WmcManager *arg1 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(WmcManager **)&jarg1; 
  result = (SddWmc)wmc_propagate(arg1);
  jresult = (jdouble)result; 
  return jresult;
}

// SddWmc wmc_zero_weight(WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1zero_1weight(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jdouble jresult = 0 ;
  WmcManager *arg1 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(WmcManager **)&jarg1; 
  result = (SddWmc)wmc_zero_weight(arg1);
  jresult = (jdouble)result; 
  return jresult;
}

// SddWmc wmc_one_weight(WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1one_1weight(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  jdouble jresult = 0 ;
  WmcManager *arg1 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(WmcManager **)&jarg1; 
  result = (SddWmc)wmc_one_weight(arg1);
  jresult = (jdouble)result; 
  return jresult;
}

// SddWmc wmc_literal_weight(const SddLiteral literal, const WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1literal_1weight(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jdouble jresult = 0 ;
  SddLiteral arg1 ;
  WmcManager *arg2 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(WmcManager **)&jarg2; 
  result = (SddWmc)wmc_literal_weight(arg1,(struct wmc_manager_t const *)arg2);
  jresult = (jdouble)result; 
  return jresult;
}

// SddWmc wmc_literal_derivative(const SddLiteral literal, const WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1literal_1derivative(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jdouble jresult = 0 ;
  SddLiteral arg1 ;
  WmcManager *arg2 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(WmcManager **)&jarg2; 
  result = (SddWmc)wmc_literal_derivative(arg1,(struct wmc_manager_t const *)arg2);
  jresult = (jdouble)result; 
  return jresult;
}

// SddWmc wmc_literal_pr(const SddLiteral literal, const WmcManager* wmc_manager);
JNIEXPORT jdouble JNICALL Java_jni_SddLibJNI_wmc_1literal_1pr(JNIEnv *jenv, jclass jcls, jlong jarg1, jlong jarg2) {
  jdouble jresult = 0 ;
  SddLiteral arg1 ;
  WmcManager *arg2 = (WmcManager *) 0 ;
  SddWmc result;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(WmcManager **)&jarg2; 
  result = (SddWmc)wmc_literal_pr(arg1,(struct wmc_manager_t const *)arg2);
  jresult = (jdouble)result; 
  return jresult;
}




//moving and adding variables to manager


// void add_var_before_lca(int count, SddLiteral* variables, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_add_1var_1before_1lca(JNIEnv *jenv, jclass jcls, jint jarg1, jlongArray inJNIArray, jlong jarg3) {
  int arg1 = jarg1;
  
  jlong *inCArray = (*jenv)->GetLongArrayElements(jenv, inJNIArray, NULL);
  if (NULL == inCArray) return;
  jsize length = (*jenv)->GetArrayLength(jenv,inJNIArray);
  SddLiteral arg2[length];
  for(int i=0; i<length; i++){
    arg2[i]=(SddLiteral) inCArray[i];
  }
  
  SddManager *arg3 = *(SddManager **)&jarg3;
  
  (void)jenv;
  (void)jcls;
  
  add_var_before_lca(arg1, arg2, arg3);
  
  for(int i=0; i<length; i++){
    inCArray[i]=(jlong) arg2[i];
  }
  
  (*jenv)->ReleaseLongArrayElements(jenv, inJNIArray, inCArray,0);

}

// void move_var_before_first(SddLiteral var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_move_1var_1before_1first(JNIEnv *jenv, jclass jcls,jlong jarg1, jlong jarg2) {
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  move_var_before_first(arg1,arg2);
}

// void move_var_after_last(SddLiteral var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_move_1var_1after_1last(JNIEnv *jenv, jclass jcls,jlong jarg1, jlong jarg2) {
  SddLiteral arg1 ;
  SddManager *arg2 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = *(SddManager **)&jarg2; 
  move_var_after_last(arg1,arg2);
}

// void move_var_before(SddLiteral var, SddLiteral target_var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_move_1var_1before(JNIEnv *jenv, jclass jcls,jlong jarg1, jlong jarg2, jlong jarg3) {
  SddLiteral arg1 ;
  SddLiteral arg2 ;
  SddManager *arg3 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = (SddLiteral)jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  move_var_before(arg1,arg2,arg3);
}

// void move_var_after(SddLiteral var, SddLiteral target_var, SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_move_1var_1after(JNIEnv *jenv, jclass jcls,jlong jarg1, jlong jarg2, jlong jarg3) {
  SddLiteral arg1 ;
  SddLiteral arg2 ;
  SddManager *arg3 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (SddLiteral)jarg1; 
  arg2 = (SddLiteral)jarg2; 
  arg3 = *(SddManager **)&jarg3; 
  move_var_after(arg1,arg2,arg3);
}


// void remove_var_added_last(SddManager* manager);
JNIEXPORT void JNICALL Java_jni_SddLibJNI_remove_1var_1added_1last(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  SddManager *arg1 = (SddManager *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(SddManager **)&jarg1; 
  remove_var_added_last(arg1);
}



#ifdef __cplusplus
}
#endif

