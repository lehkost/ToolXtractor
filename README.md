# ToolXtractor
Extract tools from TEI-encoded abstracts against a matching list.

# Build with [Maven](https://maven.apache.org/)
```mvn clean package```

# Launch the tool
```java -jar target/ToolXtractor-full.jar -byTool -dir data/2015/ -inputTools src/main/resources/tools_teresah.txt```

## Options
Usage: ```java -jar ToolXtractor-full.jar```

 ```-byAbstract```
 Results will be a list of abstracts with each tool used
 
 ```-byTool```
 Results will be a list of tools with each abstract they are mentioned in
 
 ```-dir <arg>```
 Provide path to directory to parse
 
 ```-inputTools <arg>```
 A txt file with list of the tools (1 tool per line)
 
 ```-stopwords <arg>```
 A txt file with list of the stopwords (toolnames that you don't want to appear in the results) (1 tool per line)
 
 ```-reverse```
 Reverse the order of the result (by default ascending order)
 
 ```-verbose```
 Add extra verbose information

# Dataset
All under CC and retrieved from the ADHO github page: https://github.com/ADHO/

Only for the year [2017](https://github.com/ADHO/dh2017) we used the tool [Grobid](https://github.com/kermitt2/grobid/) to create XML-TEI files.