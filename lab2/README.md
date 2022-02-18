# Lab 2

## What I have done in lab 2
* Read Chapter 2 and learn about MapReduce
* Run an example from textbook

## How to run the code
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
