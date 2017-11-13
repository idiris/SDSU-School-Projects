*----------------------------------------------------------------------
* Programmer: Mohamed Sharif-Idiris
* Class Account: masc0764
* Assignment or Title:
* Filename: prog2.s
* Date completed:  03/22/2015
*----------------------------------------------------------------------
* Problem statement:Given 3 input variables, determine the optimum dimensions of a closed cylindrical can
* Input: cost of side material, end material, and volume of can
* Output: can cost, diameter, and height
* Error conditions tested: no
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

********** CONSTANTS **********
TWO:        EQU    $40000000
PI:         EQU    $40490FDA
ONE_THIRD:  EQU    $3EAAAAAB
*******************************

start:  initIO                  * Initialize (required for I/O)
        setEVT                  * Error handling routines
        initF                   * For floating point macros only

        lineout         title
        * Cost of the End material
        lineout         prompt1   * ask the user to input the cost of the end material
        floatin         buffer    *read the users input from the keyboard
        cvtaf           buffer,D1 *convert the users input from ascii to packed floating point number and store it into D1
        move.l          D1,D4     *copy the users converted input from D1 to D4
        * Cost of the Side material
        lineout         prompt2
        floatin         buffer
        cvtaf           buffer,D2
        move.l          D2,D5
        * volume of can
        lineout         prompt3
        floatin         buffer
        cvtaf           buffer,D3
        move.l          D3,D6
        * steps to compute the diameter
        fmul            D3,D5    * multiply the volume with the side material and store the answer in D5
        fmul            #TWO,D4  *multiply the number two to the end material and store the answer in D4
        fmul            #PI,D4   *multiply the number pi to D4 and store the answer into D4
        fdiv            D4,D5    *divide D4 from D5 and store the answer into D5
        fpow            D5,#ONE_THIRD * raise the computed answer from D5 to the one third power and store it in D0
        move.l          D0,D7   *copy the radius from D0 into D7
        fmul            #TWO,D0 *multiply the radius by two and store the answer in D0
        cvtfa           diameter,#2 *store the ascii string in diameter and return 2 decimal points.
        * steps to compute the can cost
        fmul            #TWO,D6 *multiply number two to volume and store answer in D6
        fdiv            D7,D6   *divide two times the volume by the radius  and store answer in D6
        fmul            D2,D6   *multiply the side material to D6
        fmul            D7,D7   *multiply the radius by itself and store radius squared in D7
        fmul            D7,D1   *multiply radius squared to the end cost and store the answer in D1
        fmul            #TWO,D1 *multiply two to D1 and store in D1
        fmul            #PI,D1  *multiply PI to D1 and store in D1
        fadd            D6,D1   *add D1 to D6 and store answer
        move.l          D1,D0   *copy D1 to D0
        cvtfa           cost,#2 *convert from float to ascii with two decimal points on the right of the decimal
        * steps to get height
        fmul            #PI,D7  *multiply PI to radius squared and store in D7
        move.l          D3,D6   *copy D3 to D6
        fdiv            D7,D6   *divide volume by radius squared and store in D6
        move.l          D6,D0   *copy D6 into D0
        cvtfa           height,#2 *convert from float to ascii the height with two decimal points to the right of the decimal
        * printout the answers to the users input
        lineout         cancost
        lineout         diam
        lineout         heigh

        break                   * Terminate execution
*
*----------------------------------------------------------------------
*       Storage declarations

title:          dc.b    'Program #2, masc0764, Mohamed Sharif-Idiris',0
prompt1:        dc.b    'Enter the cost of end material per square cm: ',0
prompt2:        dc.b    'Enter the cost of the side material per square cm:',0
prompt3:        dc.b    'Enter the desired volume in milliliters:',0
buffer:         ds.b    80
cancost:        dc.b    'Can cost:  '
cost:           ds.b    20
diam:           dc.b    'Diameter:  '
diameter:       ds.b    20
heigh:          dc.b    'Height:    '
height:         ds.b    20



        end
