#include <jni.h>
#include <string>
#include <iostream>
#include <sstream>
#include <iomanip>
#include <fcntl.h>
#include <android/log.h>
#include <dispatch/dispatch.h>

double calculate_pi(void)
{
    __block double pi = 0;
    int n = 1000000;
    int threads = 8;
    dispatch_queue_t accumulator = dispatch_queue_create("Compute pi", NULL);

    dispatch_apply(threads, DISPATCH_APPLY_AUTO, ^(size_t idx){
        int start = idx * (n / threads);
        int end = (idx + 1) * (n / threads);
        double sum = 0;
        for (int k = start; k < end; k++) {
            double flag = (k & 1) ? -1.0 : 1.0;
            sum += flag / (2 * k + 1);
        }

        dispatch_sync(accumulator, ^{
            pi += sum;
        });
    });

    dispatch_release(accumulator);
    return pi * 4;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_example_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */)
{
    std::stringstream ss;
    ss << std::setprecision(20) << calculate_pi();
    return env->NewStringUTF(ss.str().c_str());
}
