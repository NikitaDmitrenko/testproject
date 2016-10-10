# testproject

It is a small web project which allows user send a txt files to the rest point.<br>
On the another rest point user get the json data which contain of pair value-count , 
where value - it is a line at the txt files and count - it's the number of repetitions of the line.<br>
To test a project after cloning repository go to home directory of the project , 
create .war archive by the command : <p><b>mvn clean package</b></p>
After that deploy your war archive to tomcat server folder under <b>\webapps\ROOT</b> and start server.<br>
After starting  server ,open your web browser at <b>http://localhost:8080/</b> and send multiple files to server 
or send directly on <b>http://localhost:8080/uploadMultipleFile</b> the name of file which you must to send is - <b>file</b>
