# MailCrawler

Steps for running the application

1. Open Command Terminal
2. Go to root folder (MailCrawler).
3. run mvn eclipse:eclipse
4. run mvn package
5. run java -jar -Dlog4j.configuration=file:/full_path/log4j.properties target/MailCrawler.jar -year
 
 note : if year value is provided it will take that year otherwise current year is picked.
