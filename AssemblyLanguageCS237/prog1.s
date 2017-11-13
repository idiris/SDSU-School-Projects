*----------------------------------------------------------------------
* Programmer: Mohamed Sharif-Idiris
* Class Account:masc0764
* Assignment or Title: Program #1
* Filename: prog1.s
* Date completed:03/09/2015
*----------------------------------------------------------------------
* Problem statement:Program must accept user's birthday input and compute the user's age in 2015
* Input: user's birthday
* Output: age in 2015
* Error conditions tested:no
* Included files: n/a
* Method and/or pseudocode: n/a
* References: n/a
*----------------------------------------------------------------------

*
        ORG     $0
        DC.L    $3000           * Stack pointer value after a reset
        DC.L    start           * Program counter value after a reset
        ORG     $3000           * Start at location 3000 Hex
*
*----------------------------------------------------------------------
*
#minclude /home/ma/cs237/bsvc/iomacs.s
#minclude /home/ma/cs237/bsvc/evtmacs.s
*
*----------------------------------------------------------------------
*
* Register use
*
*----------------------------------------------------------------------
*
start:  initIO                  * Initialize (required for I/O)
        setEVT                  * Error handling routines
*       initF                   * For floating point macros only
        lineout         title           * prints the title of the program
        lineout         prompt          * asks the user to input birthday day,month, and year
        linein          buffer          * reads the user's birthday input.
        cvta2           buffer+6,#4     * converts the user's input into two'scompliment
        move.l          #2015,D1        * moves the number 2015 into address D1
        sub.l           D0,D1           * subtracts the number in D1 which is 2015 from the users age input in D0
        move.l          D1,D0           * copies the computed age into address D0
        cvt2a           age,#3          * converts from two's compliment to ascii with 3 characters long
        stripp          age,#3          * gets rid of the leading zero's
        lea             age,A0          * load address of age into memory address A0
        adda.l          D0,A0           *moves contents of D0 into memory A0
        move.b          #' ',(A0)+      *puts space in memory
        move.b          #'y',(A0)+      *puts y in memory
        move.b          #'e',(A0)+      *puts e in memory
        move.b          #'a',(A0)+      *puts a in memory
        move.b          #'r',(A0)+      *puts r in memory
        move.b          #'s',(A0)+      *puts s in memory
        move.b          #' ',(A0)+      *puts space in memory
        move.b          #'o',(A0)+      *puts o in memory
        move.b          #'l',(A0)+      *puts l in memory
        move.b          #'d',(A0)+      *puts d in memory
        move.b          #'.',(A0)+      *puts  a period in memory
        move.b          #' ',(A0)+      *puts space in memory
        move.b          #'*',(A0)+      *puts y in memory
        clr.b           (A0)            *zero's out the byte
        lea             stars+35,A2     *loads the stars into memory address A2
        adda.l          D0,A2           *moves contents of D0 into memory A2
        clr.b           (A2)            *zero's out the byte
        lineout         stars           *print out the stars
        lineout         answer          *print out the prompt and the calulated age
        lineout         stars           *print out the stars
        move.b          #'*',(A2)

        break                   * Terminate execution
*
*----------------------------------------------------------------------
*       Storage declarations

title:          dc.b    'Program #1, Mohamed Sharif-Idiris, masc0764',0
prompt:         dc.b    'Enter your date of birth (MM/DD/YYYY):',0
buffer:         ds.b    80
answer:         dc.b    '* In 2015 you will be '
age:            ds.b    20
stars:          dcb.b   37,'*'

        end
