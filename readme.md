#Build paint-app
##Prerequisites
- JDK version 8 or above
- maven 

##Instructions
- Run command ```mvn clean install```
- Jar file available at location ```<project-home>/target/paint-app-1.0-SNAPSHOT.jar```
- Run command `mvn surefire-report:report`, then access reports at location ```<project-home>/target/site```

#Run paint-app
##Prerequisites
- JRE version 8 or above

##Instructions
- Run command ```java -jar target/paint-app-1.0-SNAPSHOT.jar```

#Guide to use the programx           cx  
Following commands are supported in this command line paint-app:
- Create canvas: `C width height` 
    * Example: `C 20 10`
- Create a line: `L x1 y1 x2 y2` 
    * Example Horizontal line: `L 2 3 4 3`. 
    * Example vertical line: `L 2 3 2 8`
- Create a rectangle: `R x1 y1 x2 y2` 
    * Example: `R 15 2 20 5`
- Save to file: `S filePath` 
    * Example: `S /path/filename`
    * File path can be absolute or relative to current directory
- Open file: `O filePath` 
    * Example: `/path/filename`
- Print canvas: `P`
- Quit: `Q`


