#include <iostream>
#include <semaphore.h>
#include <pthread.h>
#include <string>
#include <stdio.h>
#include <signal.h>
#include <termios.h>
#include <unistd.h>
#include <stdlib.h>
#define NUM_THREADS 3

using namespace std;
sem_t FLAG; //Semaphore flag
pthread_t threads[NUM_THREADS]; //thread array
bool run; //Flag to run alarm

//Hold values of alarm to be passed to thread
struct alarm_data{
	int hour;
	int minute;
	int sec;
};

//Signal handler is called and cancels the child threads and exits the program
void signal_handle(int signum)
{
	int cancelthread;
  	//Cancel Clock thread
	cancelthread = pthread_cancel(threads[0]);
  	//Cancel Alarm thread if created
  	if(run)
		cancelthread = pthread_cancel(threads[1]);
  	//Cancel countdown thread
	cancelthread = pthread_cancel(threads[2]);

	sem_destroy(&FLAG);
	cout << "Ending program" << endl;
	exit(0);
}

//endProg creates a signal interrupt to trigger the end of the program
void endProg()
{
	cout<<"\nSignal Terminated...\n"<<endl;
	signal(SIGTERM, signal_handle);
	cout<<"Program Exited Gracefully!\n"<<endl;
	exit(SIGTERM);
	
}

//Countdown starts at an initial value and counts down
//each second. Once done, the threads calls for the termination
//of every thread and ends the program
void *countdown(void *CD)
{
	long counter = (long)CD;
	
	while(counter >= 0)
	{
		sem_wait(&FLAG);
		cout << "Timer: " << counter << endl;
		sem_post(&FLAG);
		counter = counter -1;
		sleep(1);
	//End program
	} 
 endProg();
}

/*Alarm compares the user inputted values with the current time
of the system at every second. If alarm values equal time values,
a flag is triggered to print a message to the user.*/
void *alarm(void *AL)
{
	//alarm flags... alon = ALarmON
	bool alon = true;
	bool ring = false;
	alarm_data *data = (alarm_data *)AL;

	// Alarm time variables
	int a_hr;
	int a_min;
	int a_sec;

	// Current time variables
	int c_hr;
	int c_min;
	int c_sec;

	// typecast/convert variables for use as int
	a_hr  = (*data).hour;
	a_min = (*data).minute;
	a_sec = (*data).sec;
	
	
	while(alon) 
	{
		
		// Set current time
		time_t now = time(0);
		tm * ltm   = localtime(&now);
		c_hr   = (ltm -> tm_hour);
		c_min  = (ltm -> tm_min);
		c_sec  = (ltm -> tm_sec);

		// If alarm time equals current time, print message
		if((a_hr == c_hr) && (a_min == c_min) && (a_sec == c_sec))
		{	
			ring = true;
			alon = false;
		}
	
		sem_wait(&FLAG);	
		

		cout << "USER_ALARM set for ";
		cout << a_hr  << ":" << a_min << ":" << a_sec << endl;
		
		if(ring)
		{
			cout << "\n\n\a BEEP! \a BEEP! \a BEEP!\a"<<endl;
			cout << "\n\n!!!!ALARM IS TRIGGERED!!!!\n\n" << endl;
		}
		sem_post(&FLAG);
		sleep(1);
	}
  //Exit thread upon completion
  pthread_exit(0);	
}

//Clock prints the current time each second
void *clock(void *temp)
{
	while(1) 
    	{

      		// Current time based on current system
    		time_t now = time(0);
   			tm * ltm = localtime(&now);
			sem_wait(&FLAG);
        	// Print current time to console
			cout << "Current time: ";
        	cout << ltm -> tm_hour << ":";
        	cout << ltm -> tm_min << ":";
        	cout << ltm -> tm_sec << endl <<endl;
        	sem_post(&FLAG);
        	sleep(1);
    	}
}

int getch(void)
{
    struct termios oldattr, newattr;
    int ch;

    tcgetattr( STDIN_FILENO, &oldattr );
    newattr = oldattr;
    newattr.c_lflag &= ~( ICANON | ECHO );
    newattr.c_cc[VMIN] = 1;		//blocks read until a single character is available
    tcsetattr( STDIN_FILENO, TCSANOW, &newattr );

    ch = getchar();

    tcsetattr( STDIN_FILENO, TCSANOW, &oldattr );

    return ch;
}

//Monitor the user's input until x is entered. Then end the program.
void checkC()
{
	char ctrl;
	bool notC = true;
	while(notC)
	{
		ctrl = getch();
		
		if(ctrl == 'c')
		{
			cout<<"\n\n!!!!Program killed by USER!!!!"<<endl;
			endProg();
			notC = false;
		}
	}
}

void init_pthreads(alarm_data alarmTi, int timer)
{
    //Initialize semaphore FLAG
  	sem_init(&FLAG, 0, 1);
  
  	//Initialize POSIX Threads and attribute variables
  	
    	int stat1;
	int stat2;
	int stat3;

	pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);
	pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

        //Run threads for  the Clock, Alarm, and Countdown
        stat1 = pthread_create(&threads[0], NULL, clock, (void *) NULL);
        if(stat1) 
        {
            cout << "ERROR" << stat1 << endl;
            exit(-1);
        }
  		
  		if(run)
        {  
        stat2 = pthread_create(&threads[1], NULL, alarm, (void *) &alarmTi);
          if(stat2) 
          {
              cout << "ERROR" << stat2 << endl;
              exit(-1);
          }
        }
	    stat3 = pthread_create(&threads[2], NULL, countdown, (void *) timer);
        if(stat3) 
        {
            cout << "ERROR" << stat3 << endl;
            exit(-1);
        }

}

int main(int argc, char *argv[])
{ 
	alarm_data alarmTi; //holds thehour, minute, and seconds of the Alarm
	int timer; //holds the given timer or default timer 
	
	//if the user has a 2nd entry, then arg[1] is set to timer
	if(argc == 2)
	{
	timer = atoi(argv[1]);	
	}
	//if user enter full alarm entries arg's 2-5, save the parameters
	else if(argc == 5)
	{
		alarmTi.hour = atoi(argv[2]);
		alarmTi.minute = atoi(argv[3]);
		alarmTi.sec = atoi(argv[4]);
		timer = atoi(argv[1]);
      		

		run = true;
	}
  	else if(argc == 4)
    {
        	alarmTi.hour = atoi(argv[2]);
		alarmTi.minute = atoi(argv[3]);
		alarmTi.sec = atoi(argv[4]);
		timer = 30;
      	run = true;
    }
	else
	{
        
	cout << "\n\nNo alarm time given...\n" << endl;
        cout << "Using default timer for countdown: " << endl;
	
		timer = 30;
		run = false;
	}
	cout<<"\n           ----------"<<endl;
        cout<<"COUNTDOWN |" << timer << " seconds|"<< endl;
	cout<<"           ----------"<<endl;

	//Initalize pthreads and semaphores
	init_pthreads(alarmTi, timer);

	//check for C character entry by user to terminate
	checkC();
	
	//gracefull exit message
	cout<<"Program exited Gracefully"<<endl;	
    
	return 0;
}
