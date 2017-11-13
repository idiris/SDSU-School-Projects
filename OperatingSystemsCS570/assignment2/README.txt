
John Ervin 
(Edoras acct # cssc 1137)


Mohammad Sharif [available for testing in the turn in on Blackboard and in the README file]
(Edoras acct # cssc 1135)


COURSE INFORMATION:
Prof- Guy Leonard
Course- CS 570 
Session- Summer 2017
 
Assignment 2 : Page Replacement Simulation

File Name: README.txt

FILE MANIFEST (name of all files included in this project):

1. README.txt
2. a2.cpp
3. a2.h
4. Makefile
5. clock.cpp
6. clock.h
7. opt.cpp
8. opt.h
9. pages.txt
10. second_chance.cpp
11. second_chance.h
12. assign2.cpp
13. assign2.h

COMPILE INSTRUCTIONS:

Compile the program and execute it on Edoras using the gcc, or g++ compiler. Go into the a2 sub directory for the source files and there are sufficient comments that make the source easy to read. Review the comments for help and then run the Makefile (In the cmd line enter make then press enter) which will create an executable named "a2" in this same directory (~/a1). Now you can operate program you must enter "make" in the command line, the program will create the a2 executable. Then the user must enter "./a2" in the command line of the current working directory. 

OPERATING INSTRUCTIONS:

When you enter "make" then "./a2" in order as command line entries, the program builds a simulation of the page replacement function of the page fault interrupthandler routine for a paged memory management system. We implementd the following Page Replacement Algorithms: 

1. OPT
2. 2nd Chance
3. Clock

Upon startup, you will be prompted for the number of frames in main memory. Then it Reads the number sequence of pages accessed from the file "pages.txt" file. Then, the program Runs each of the algorithms: OPT, Second Chance, and Clock - on the same input string. Finally, The program Prints out the number of faults each algorithm generated to the terminal. I have show example code below to represent a normal output:

/***********************************************/
						/
Number of Frames: 3				/
OPT Generates [ 6 ] Page Faults			/
Second Chance Generates [ 6 ] Page Faults	/	
Clock Generates [ 9 ] Page Faults		/
[cssc1137@edoras a2]$				/	
						/
/***********************************************/
 

ADDITIONAL NOTES:

The program simulates a process/thread accessing its pages during execution. Thesequence of pages the page/thread accesses will be contained in a file, named "pages.txt" which our program magically creates. This file shall be located in the same directory as the executable and shall contain a series of positive integers representing the pages (by it's page number) which the process/thread is accessing as it runs. The limit of number of pages any process/thread may have is 99. The pages.txt file will contain a single line of text where each number is separated by a space character.

LESSON LEARNED:

Our program implemented bots that each run in their own thread simultaneously but not asynchronously with each other. We learned to managed the bots to execute a timed thread and print out quotes by Brian Kernigan, and Edsger Dijkstra to a shared file named QUOTE.txt. 

In this process, we also learned to use an appropriate IPC mechanism/algorithm to prevent data from getting corrupted by the other bot. We tested this implementation by running the program and checking that the output file QUOTE.txt properly received quotes from both bots that printed alternatively because we implemented an even-odd algorithm to determine which bot gets to write.   

ANALYSIS OF THE PAGE RELACEMENT ALGORITHMS:

EXPERIMENT1:

Page String (pages.txt): 2 3 2 1 5 2 4 5 3 2 5 2

TEST #1 RESULTS 3-FRAMES:

Number of Frames: 3				
OPT Generates [ 6 ] Page Faults			
Second Chance Generates [ 6 ] Page Faults		
Clock Generates [ 9 ] Page Faults		

						
TEST #2 RESULTS 5-FRAMES:

Number of Frames: 5				
OPT Generates [ 5] Page Faults			
Second Chance Generates [ 5 ] Page Faults		
Clock Generates [ 5 ] Page Faults		

TEST #3 RESULTS 7-FRAMES:

Number of Frames: 7				
OPT Generates [5] Page Faults			
Second Chance Generates [ 6 ] Page Faults		
Clock Generates [ 5 ] Page Faults		

EXPERIMENT 2:

Page String (pages.txt)- 4 7 1 8 6 9 6 5 7 2 4 2 4 5 2 7


TEST #1 RESULTS:

Number of Frames: 3				
OPT Generates [10] Page Faults			
Second Chance Generates [ 15 ] Page Faults		
Clock Generates [ 12 ] Page Faults		

TEST #2 RESULTS:

Number of Frames: 5				
OPT Generates [9] Page Faults			
Second Chance Generates [ 13 ] Page Faults		
Clock Generates [ 10 ] Page Faults		

TEST #3 RESULTS:

Number of Frames: 7				
OPT Generates [9] Page Faults			
Second Chance Generates [ 10 ] Page Faults		
Clock Generates [ 5 ] Page Faults		

EXPERIMENT NOTES:
throughout our experiments we noticed that OPT performed well in every test as outlined in our course book. 2nd chance had the 2nd highest amount of faults throuought all of our algorithm experiments. The clock algorithm performed the worst overall however, it produced lower numbers than 2md chance when we added more frames to the experiment.
