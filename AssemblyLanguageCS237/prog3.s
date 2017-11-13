*----------------------------------------------------------------------
* Programmer: Mohamed Sharif-Idiris
* Class Account: Masc0764
* Assignment or Title: Program #3
* Filename: prog3.s
* Date completed:  04/15/2015
*----------------------------------------------------------------------
* Problem statement:Read an unsigned 16 bit base 10 number from the keyboard,and convert it to a base specified by the user( 2 .. 16 ).
* Input: Base 10 number
* Output: Convert to the desired base
* Error conditions tested: yes
* Included files: n/a
* Method and/or pseudocode: yes
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
        lineout         title
Do:
        lineout         prompt
        linein          buffer
        lea             buffer,A1
        * 1st Validation,strip and make sure there are not more than 5 digits inputed
        stripp          buffer,D0
        clr.l           D5
        move.l          D0,D7
        cmpi.l          #5,D7   *compare the users input to 5
        BHI             lon     *if the input is greater than 5,branch to lon(error message)
        *2nd Validation , integers 0..9 and not letters etc.
         subi.l         #1,D7
val:
        cmpi.b          #$30,(A1) *compare the contents of A1(users input) to ascii 0
        BLO             inval     *if less than 0 go to inval(error message)
        cmpi.b          #$39,(A1)+
        BHI             inval     *if higher than 9 go to inval(error message)
        dbra            D7,val    *loop back to val to make sure every digit inputed is range between 0 and 9
        bra             check2    *if this validation checks out go to the third valdation check2
check2:
        *3rd validation, between 0-65535
        cvta2           buffer,D0  *convert input
        move.l          D0,D4      *copy converted user input to D4
        tst.l           D4         *test to make sure it equals 0
        BHS             check      *if it is higher than 0 or equal branch to check(secondary validation)
        bra             next       *if everything is validated and checks out branch to next(base portion)

check:  cmpi.l  #65335,D4          * subtract the users input from 65535
        BHI     error2             *if higher than 65535 branch to error2
        bra     next               *else everything checks out go to next(base portion)

*print out error message and branch back to the beginning Do
inval:  lineout         er
        bra             Do
*print out error message and branch back to beginning Do
lon:    lineout         long
        bra             Do
*print out error message and branch back to beginning Do
error2: lineout         error3
        bra             Do
*begin base portion and ask the user to input what base to convert to
next:
        lineout prompt2
        linein          buffer
        lea             buffer,A2
        stripp          buffer,D0
        *validate that the user doesnt input more than 2 numbers
        move.l          D0,D2
        cmpi.l          #2,D2
        BHI             lon2
        subi.l          #1,D2
        *bra            next1
        *validate that the input is only digits between 0..9
check3:
        cmpi.b          #$30,(A2)
        BLO             inval2
        cmpi.b          #$39,(A2)+
        BHI             inval2
        dbra            D2,check3    *loop several times to make sure every digit inputed is range between 0 and 9
        bra             check4
        bra             next1
        *validate that the base is between 2..16

check4:
        cvta2           buffer,D0
        move.l          D0,D3
        cmpi.b          #2,D3
        BHS             check5
        bra             error4
check5:
        cmpi.l          #16,D3
        BHI             error4
        bra             next1
*print out error messages and branch back each time to beginning of the base portion next
lon2:   lineout         long2
        bra             next
inval2: lineout         err
        bra             next
error4: lineout         error5
        bra             next
*divide by base and load remainder
next1:

        lea     Rem,A3
div     tst.l   D4      *test to see if it equals to 0
        beq     next2   *if equal,stop dividing and branch to next2(output portion)
        divu    D3,D4   *divide the base from the users input and copy the answer in D4
        swap    D4      *swap to put the remainder before the quotient
        cmpi    #10,D4  *compare that the remainder is greater than 10
        BHS     add     *if its greater than or equal to 10 branch to add
        addi.w  #$30,D4 *if not its less than 10 and continue adding $30 to the remainder
goto:   move.b  D4,(A3)+ * copy the remainder into the contents of A3
        swap    D4       *swap back to continue dividing
        ext.l   D4
        addq.l  #1,D5
        bra     div      *after process complete branch back to div to continue dividing
add:    addi.w  #$37,D4 * add $37 to make the digits bigger than 10 print A-F
        bra     goto    *after you add branch to goto to continue copying the reminder into the contents of A3

next2:  lea     output,A4
        tst.w   D5
        subq.w  #1,D5
loop:   move.b  -(A3),(A4)+
        dbra    D5,loop
        clr.b   (A4)
        lineout answer



        break                   * Terminate execution
*
*----------------------------------------------------------------------
*       Storage declarations

title:  dc.b    'Program #3, Mohamed Sharif-Idiris, masc0764',0
prompt: dc.b    'Enter a base 10 number:',0
buffer: ds.b    80
er:     dc.b    'Error, please input an integer ie 0...9',0
long:   dc.b    'Error, please input less than 5 digits',0
error3: dc.b    'Error, please be between 0..65535,',0
prompt2:dc.b    'Enter the base to convert to:',0
long2:  dc.b    'Error, please input 2 integers or less only',0
err:    dc.b    'Error, please input an integer only. ie 0...9',0
error5: dc.b    'Error, please input bases between 2..16,',0
Rem:    ds.b    20
answer: dc.b    'The answer is: '
output: ds.b    20

        end
