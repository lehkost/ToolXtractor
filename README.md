# ToolXtractor
Extract tools from TEI-encoded abstracts against a matching list.

# Build with mvn
mvn clean package

# Launch the tool
java -jar target/ToolXtractor-jar-with-dependencies.jar -byTool -dir data/2015/ -inputTools src/main/resources/tools_teresah.txt

# Dataset
All under CC and retrieved from the ADHO github page: https://github.com/ADHO/

Only for the year [2017](https://github.com/ADHO/dh2017) we used the tool [Grobid](https://github.com/kermitt2/grobid/) to create XML-TEI files.