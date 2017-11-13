#include<stdio.h>

int OPT(int frames, int pages[], int counter) {
	int fault = 0;
	int trackr[frames+1];
	int i,j,k,m,n,p,x,tmp,flag;
	int array[frames+1];
	//initialize the frames array
	for(i=0; i<frames; i++) {
		array[i] = -1;
	}
	
	for(i=0; i<counter; i++) { //iterate through pages[]
		flag = 0;
		for(j=0; j<frames; j++) { //iterate array[]
			if(array[j]==pages[i]) {
				flag = 1;
			}
		}//ENDFOR-ARRAY
		
		if(flag==0) {
			array[frames-1] = pages[i];
			fault++;
		}
		
		//reset 
		for(k=0; k<frames; k++) {
			trackr[k] = 100;
		}
		for(m=0; m<frames; m++) {
			for(n=i+1; n<counter; n++) {
				if(array[m]==pages[n]) {
					trackr[m] = n;
					break;
				}
			}
		}
		for(p=frames-1; p>0; p--) {
			if(trackr[p] < trackr[p-1]) {
				tmp = array[p];
				array[p] = array[p-1];
				array[p-1] = tmp;					
			}
		}
		//print out of frames and fault
		for(x=0;x<frames;x++) {
			printf("%d ",array[x]);
		}
		if(flag==0)
			printf("fault");
		printf("\n");
	}
	return fault;
}//END OPT

int Clock(int frames, int pages[], int counter) {
	int i,j,m,n;
	int fault=0 ,flag=0;
	int array[frames+1];
	//initialize the frames array
	for(i = 0; i < frames; i++) {
		array[i] = -1;
	}
	
	j = 0;
	for(i = 0; i < counter; i++) {
		flag = 0;
		for(m = 0; m < frames; m++)
			//check to see if current value in frames
			if(array[m] == pages[i]) {
				flag = 1;
			}
			//if not already in frames
			if(flag == 0) {
				array[j] = pages[i];
				j = (j+1)%frames;
				fault++;
				flag = 0;
			}
		//print out of frames and fault
		for(n=0;n<frames;n++) {
			printf("%d ",array[n]);
		}
		if(flag==0)
			printf("fault");
		printf("\n");	
	}
	return fault;
}
void runAlgorithms(int frames) {
	int counter = 0;
	int i = 0;
	int j = 0;
	int tmp = 0; 
	int pages[100];
	int array[frames+1];
	
	//read  the sequences of pages accessed from the file pages.txt
	FILE *file = fopen("pages.txt", "r");
	while(fscanf(file,"%d ",&tmp) != EOF) {
		pages[counter] = tmp;
		counter++;
	}

	
	//printf("Running the Second Chance Algorithm: \n");
	//int secondchance_fault = Clock(frames, pages, counter);
	printf("Running OPT: \n");
	int opt_fault = OPT(frames, pages, counter);

	printf("Running the Clock Algorithm: \n");
	int clck_fault = Clock(frames, pages, counter);
	
	//print out the number of faults each algorithm generates
	printf("Number of Frames: %d \n",frames);
	printf("OPT Generates %d Page Faults: \n",opt_fault);
	//printf("Second Chance Generates %d Fault:   \n",secondchance_fault);
	printf("Clock Generates %d Page Faults: \n",clck_fault);
	
	fclose(file);
}

int main() {
	int frames;
	int opt_fault;
	int secondchance_fault;
	int clck_fault;
	
	//prompt the user for the number of frames in Main Memory
	printf("How many frames in main memory? \n");
	scanf("%d",&frames);
	
	//run the 3 algorithms
	runAlgorithms(frames);
}
