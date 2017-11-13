#include "assign1.h"

void *thread_check(void *threadid) {
    long threadID = (long)threadid;     
    file.open("QUOTE.txt", fstream::app); 
    // Use the semaphore to restrict access to 1 thread at a time
    sem_wait(&FLAG);  

   //Even every 2 seconds and print the quote to the file 
    if(threadID%2 == 0){ 
        file  << " "<<threadID << "\"Controlling complexity is the essence of computer programming.\" -- Brian Kernigan \r\n" << endl;
        sleep(2);  
        cout << "Thread " << threadID << " is running." << endl;    
    }

    //if odd every 3 seconds print the quote to the file    
    else {  
        file << " "<<threadID <<  "\"Computer science is no more about computers than astronomy is about telescopes.\" --Edsger Dijkstra \r\n"<< endl;
        sleep(3);   
        cout << "Thread " << threadID << " is running." << endl; 
 
    }

    //Exit thread
    sem_post(&FLAG); 
    file.close();  
    pthread_exit(NULL);
}
 
int main() {
 
    // Created a file and write its running process id
    file.open("QUOTE.txt"); 
    file << "Process ID = " << getpid() << "\n" << endl;
 
    sem_init(&FLAG, 0, 1);   
    pthread_t threads[NUM_THREADS]; //Create threads variable with 7 threads
    
    for(long j=0; j<1; j++) {
        for(long i=0; i<7; i++) {
            pthread_create(&threads[i], NULL, thread_check,(void *)i );
            (void) pthread_join(threads[i],NULL);
 
        }
    
    }          

    //Destroy the semaphore and exit the program
    pthread_exit(&FLAG);
    sem_destroy(0);
    cout << "The Program has completed" << endl;
}
 

