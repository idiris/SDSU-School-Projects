John Ervin 
(Edoras acct # cssc 1137)


Mohammad Sharif [available for testing in the turn in on Blackboard and in the README file]
(Edoras acct # cssc 1135)


COURSE INFORMATION:
Prof- Guy Leonard
Course- CS 570 
Session- Summer 2017
 
Assignment 3 : Interruptable clock and timer

File Name: README.txt

FILE MANIFEST (name of all files included in this project):

1. README.txt
2. a3.cpp
3. Makefile
4. aclock

COMPILE INSTRUCTIONS:

Compile the program and execute it on Edoras using the gcc, or g++ compiler. Go into the a3 sub directory for the source files and there are sufficient comments that make the source easy to read. Review the comments for help and then run the Makefile (In the cmd line enter make then press enter) which will create an executable named "aclock" in this same directory (~/a3). Now you can operate program by entering "make" in the command line, the program will create the aclock executable. Then the user must enter "./aclock" in the command line of the current working directory with time inputs as your parameters if you choose. 

OPERATING INSTRUCTIONS:

When you enter "make" then "./aclock" in order as command line entries without time parameters. The program will do a default 30 second count down and exit gracefully. However if you enter a command such as "./aclock 90" the program builds and runs for 90 seconds, since it was specified. 

Moreover, One is able to set an alarm by entering the 3 parameters- HOURS MIN SECONDS- respectively after the "./aclock 90". This will set an alarm for the USER that will display a message and the system will also BEEP to alert the user and then continue with the program. 

NOTE: Please Be aware that if you alarm exceeds the countdown timer, the program will terminate before the user is alerted. If the User alarm sounds BEFORE the countdown timer...the user will be alerted and the countdown will continue until it exits gracefully. 


Example Input/Output:

/******************************************/

[cssc1137@edoras ~]$ ls
a1  a2  a3  assign2  assign3  test
[cssc1137@edoras ~]$ cd a3
[cssc1137@edoras a3]$ make
g++ -w -Wall -lpthread -lm -lrt -o aclock a3.cpp
[cssc1137@edoras a3]$ aclock 5 20 01 04

           ----------
COUNTDOWN |5 seconds|
           ----------
Timer: 5
Current time: 20:1:2

Alarm set for 20:1:4
Alarm set for 20:1:4
Current time: 20:1:3

Timer: 4
Alarm set for 20:1:4


 BEEP!  BEEP!  BEEP!


!!!!ALARM IS TRIGGERED!!!!


Timer: 3
Current time: 20:1:4

Timer: 2
Current time: 20:1:5

Timer: 1
Current time: 20:1:6

Timer: 0
Current time: 20:1:7


Signal Terminated...

Program Exited Gracefully!

[cssc1137@edoras a3]$

/******************************************/

ADDITIONAL FEATURES/Extra Credit:

While the program is in execution the user is able to use ctrl+c to cancel the program. On edoras one must use alt+c instead. this method prevented us from changing our stty user settings. The program will output the following:

/******************************************/
					   
[cssc1137@edoras a3]$ aclock 23   	   

           ----------
COUNTDOWN |23 seconds|
           ----------
Timer: 23
Current time: 20:8:26

Timer: 22
Current time: 20:8:27

Timer: 21
Current time: 20:8:28


!!!!Program killed by USER!!!!

Signal Terminated...

Program Exited Gracefully!

[cssc1137@edoras a3]$

/******************************************/

LESSON LEARNED:

Our team learned the basic fundamentals of creating a clock in a program. We also learned to use interrupts to stop the proces/program for additional functionality.
