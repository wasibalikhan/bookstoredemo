Technology Stack:

Java 11
SpringBoot 2.7.3
H2 In Memory database
Docker
lombok
Maven as project build tool
Steps To Run The Application

1. Server Port is 8085

2. Discount percentage is reading from properties file.

3. Three different end points exposed. 
   a. Save book end point http://localhost:8085/saveBook 
   Required JSON request to save book information: 
   { "name": "fiction book", "description":"WAK Book1", "bookType": "fiction", "author": "Wasib Ali Khan", "price" : 200.00, "isbnno" : 1234 } 
   or 
   { "name": "comic book", "description":"WAK Book2", "bookType": "comic", "author": "Wasib Ali Khan", "price" : 100.00, "isbnno" : 12345 }
   or
   { "name": "science book", "description":"WAK Book3", "bookType": "sport", "author": "Wasib Ali Khan", "price" : 100.00, "isbnno" : 12344 }

   b. Get particular book by name search
      http://localhost:8085/getBook/Autobiography1

   c. Get all books with payable amount 
	   http://localhost:8085/getAllBooks
	   
   d. Update book by id
      http://localhost:8085/book/1
	  
   e. Delete book by Id
      http://localhost:8085/books/1
   
   f. Delete all books
     http://localhost:8085/books   


Note: Docerization available for this project and  from docker image project can run. 

Build a docker image docker 
   build -t book-store-docker.jar .

Run the image
  docker run -d -p 8080:8085 book-store-docker.jar   
