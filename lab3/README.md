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
javac -classpath `hadoop classpath` MaxTemperatureMapper.java
javac -classpath `hadoop classpath` MaxTemperatureReducer.java
javac -classpath `hadoop classpath`:. MaxTemperature.java
```
Create the JAR file
```
jar cvf maxTemp.jar *.class
```
Run the program
```
hadoop fs -mkdir /user/<yourNetID>/lab2
hadoop fs -put temperatureInputs.txt /user/<yourNetID>/lab2

hadoop jar maxTemp.jar MaxTemperature /user/<yourNetID>/lab2/temperatureInputs.txt /user/<yourNetID>/lab2/output
```
Check out the results
```
hadoop fs -ls /user/<yourNetID>/lab2/output
hadoop fs -cat /user/<yourNetID>/lab2/output/part-r-00000
```
