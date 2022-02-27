# Lab 3
For lab 3, we are required to implement a simplified PageRank with MapReduce.

## What I have done in lab 3
* Read Lecture 5 slides about programming MapReduce applications
* Set up a Maven project in Intellij for coding and debugging locally 
* Write my own code to implement a PageRank algorithm with MapReduce
* Modify the code to call the MapReduce job 3 or more times
* Set the job to run 10 times and compare the results

## Description
## Algorithm
[The simplified pagerank algorithm](https://en.wikipedia.org/wiki/PageRank#Algorithm) is enough for this lab. What I have done is to parse the input file, compute the PageRank and record the source and targets for each page and add up the PageRank for each page.
### PageRankTuple
A custom data structure is created storing `outlink_targets`, `source_page`, and `page_rank` for mapper output
The function `toString()` is overridden according to the requirements of the output.

### Mapper
The output pair of the Mapper would be like: `<outlink_target>: <source_page, PR/number_of_outlinks>`. Also, the original outlinks information is also needed for final output so another Mapper output would be like `<source_page>: <outlink_targets>`.
I use regex pattern matching to parse the inputs and generate the Mapper outputs with PageRankTuple. 

### Reducer
The Reducer is the simplest, it just adds up the output from the Mapper. (Most of the work has done by the framework). However, there are a pit here. The reducer reduces twice for some reason, so the condition of when to set out-link targets is important. Otherwise, the result may be lost.

### Driver/main
Most of the parts in main function don't need to be changed. I implemented a for loop since we are required to run the job 3 times and I extended this requirement to enable the program to run the job any times. A separator is also added to give a better format of the output.

## How to run the code
Go to source code directory `src/main/java`
```
cd src/main/java
```

Compile the Java classes
```
javac -classpath `hadoop classpath` PageRankTuple.java
javac -classpath `hadoop classpath`:. PageRankMapper.java
javac -classpath `hadoop classpath`:. PageRankReducer.java
javac -classpath `hadoop classpath`:. PageRank.java
```
Create the JAR file
```
jar cvf pageRank.jar *.class
```
Run the program
```
hadoop fs -mkdir /user/xw2788/lab3
hadoop fs -mkdir /user/xw2788/lab3/input
hadoop fs -put <inputfile> /user/xw2788/lab3/input

hadoop jar pageRank.jar PageRank /user/xw2788/lab3/input /user/xw2788/lab3/output
# or you can run mapreduce job 3 times
hadoop jar pageRank.jar PageRank /user/xw2788/lab3/input /user/xw2788/lab3/output 3
```
Check out the results
```
hadoop fs -ls /user/xw2788/lab3/output
hadoop fs -cat /user/xw2788/lab3/output/part-r-00000
```
Remove the output files before the next run
```
hadoop fs -rm -r /user/xw2788/lab3/output
```
## Screenshots
### Compile Java program
![Snipaste_2022-02-27_16-03-05](https://user-images.githubusercontent.com/39271899/155900524-1306c28d-e7e6-4387-b039-93f30a0465bf.png)

### Run the program in Hadoop
![Snipaste_2022-02-27_16-09-17](https://user-images.githubusercontent.com/39271899/155900525-156d87c8-cf20-468b-bb3b-d581a1cf5aa5.png)

### Check the output
![Snipaste_2022-02-27_16-15-17](https://user-images.githubusercontent.com/39271899/155900526-d0ef9aef-69f4-482b-b42c-36bde8ca87ff.png)

### Remove the previous output and re-run the job 3 times
![Snipaste_2022-02-27_16-22-55](https://user-images.githubusercontent.com/39271899/155900527-a7bf7d55-7fa3-4d22-b1e8-417fc2a92d42.png)

### Check the outputs
![Snipaste_2022-02-27_16-25-37](https://user-images.githubusercontent.com/39271899/155900528-ae0272bb-be2d-4571-bf66-3a0597146ffd.png)
