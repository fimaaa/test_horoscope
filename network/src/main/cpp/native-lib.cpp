//
// Created by Fiqri Malik Abdul Az on 5/28/2021.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_network_di_ExternalDataKt_getReleasePokemonUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://pokeapi.co/api/");
}