d
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include <stdint.h>
#include <fstream>
#include <string>
#include <semaphore.h>
#include <pthread.h>
#define NUM_THREADS 7

using namespace std;
sem_t FLAG;  // Create semaphore named Flag which threads will use
ofstream file; //file to write to in the cwd

void *thread_check(void *);

