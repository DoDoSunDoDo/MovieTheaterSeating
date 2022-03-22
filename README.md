# MovieTheaterSeating

### Overview

This application is design for assigning seats within a movie theater to fulfill reservation request. 

### Assumptions

1. The total capacity for this movie theater is 10 (rows) X 20 (seats/per row). The row is marked from Letter 'A' to 'J' from front row to back row. Each seat in each row is marked from number '1' - '20'.
2. For the purpose of public safety, there are three seats buffered distance in each row between different cohort. There is one row buffered distance between each row that occupied by customers.
3. To increase customer experience, there always first come first served. Customer who order frist can be assigned the seats in the middle. The larger distance from middle row, the less preference/priority the row has. That means the first row and last row has the least priority. 
4. In each row, seats are assigned from number 1 to number 2. 
5. If there are too many people in one cohort that single row cannot hold all members, the cohort will be divided into subgroup. To increase customer experience, dividing time should be as less as possible. That means we try to group as many people as possible in each subgroup. This therefore allow customers enjoy their party time. 
6. If there are not enough seats for one cohort (group A), theater cannot serve this cohort for current round. If there are still reservation request from another cohort(group B) with less people who order after group A, we can serve group B first. We will serve group A in next show time.

### How to use

1. Cloen this repo and navigate to "src" folder.
2. Compile all java files in package com.walmart.seating. <br> For example: ```javac MovieTheater.java``` Recommend to use IDE (IntelliJ).
3. Use MovieTheaterDrive.java to run the program. User need to provide valid input file path and valid output file path to run the program. This two paths is seperated by one space. Current version only support for .txt file.  <br>For example: ```java MovieTheaterDriver $inputFilePath $outputFilePath``` <br>Another example: ```java MovieTheaterDriver C:\User\this\is\valid\input\path\input.txt C:\User\this\is\valid\output\path\output.txt```
    <br> Input file is in the format of "R"+ $threeDigitNumberStartFrom1 + $space + $ticketNumberPerGroup. <br>
    For example: 
```
R001 2
Roo2 200
```
4. Navigate to the output folder and check the assigned seats in $output.txt <br>
    Output file is in the format of "R" + $threeDigitNumberStartFrom1 + $space + $ReservedTicketNumber<br> 
If seats are not enough for one request, the message "not enough seat for this reservation!" will be shown. For example:
```
R001 F1,F2
Roo2 not enough seat for this reservation!
```
5. For testing purpose, this step is optional. Recommend to use IDE (IntelliJ).<br>
5.1 Navigate to "test" folder. <br>
5.2 Compile MovieTheaterTest.java file. <br>
5.3 Run individualTest() function to run ten provided tests. The input file provided in "input" folder. The output file will be generated in "output" folder. The expected file provided in "expected" folder. <br>
5.4 Compare result in $output.txt and $expected.txt. <br>
5.5 Customized your own designed ticket reservation if you are interested in. Manually edit input.txt file under "file" folder. Check the corresponding output.txt file under "file" folder. 