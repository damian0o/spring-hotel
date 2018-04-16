# spring-hotel
Simple hotel Rest API based on Spring Boot, Java 8, Hibernate

Hotel application allow for features as listed:

Hotel administrator perspective

* List all available hotels
* Create new hotel
* List all available rooms for specified hotel
* Create new rooms for specified hotel

Hotel client perspective

* List available rooms for specified parameters
    * Arrival date (start)
    * Leaving date (end) 
    * Desired city (city)
    * Minimum price for room (min)
    * Maximum price for room (max)
    
## Build application

To build application using maven tool. Write below command in command line from root project dierectory.

    mvn clean install
mvnw - maven wrapper could be used too*

## Start application

From project directory

    mvn spring-boot:run
    
or start compiled jar from target directory after application build step

    java -jar target/spring-hotel-0.0.1-SNAPSHOT.jar

## Application testing

#### Postman

Postman collection is available in postman directory. Just import it to postman application.

#### httpie

Available at https://httpie.org/

Hotel administration api

command | description
--------|--------
http GET :8080/hotel | List all hotels
http POST :8080/hotel name:='"HotelName"' city:='"City"' | Create new hotel
http GET :8080/hotel/HotelName | Get info about hotel with given name
http GET :8080/hotel/HotelName/room | Get hotel rooms list
http POST :8080/hotel/HotelName/room price:='200' | Add room with price to hotel configuration
     
     
Hotel client api

command | description
----|----
http GET :8080/reservation start=="2016-02-01" end=="2016-02-03" city=="Poznan" min=="100" max=="250" | List available room for specified parameters |
http POST :8080/reservation start:='"2017-05-24"' end:='"2017-05-26"' roomId:='1' | Add new reservation


## TODO

* Allow admins to accept reservations
* Add spring security and create separate roles for administrators and clients
* Registration endpoint for clients and hotel administrators
* Serve resources only to owners (hotels)
* Allow to list or cancel client reservations
* Document api with swagger
* Add missing JavaDocs to code base
* Change unchecked Runtime exception to checked. Add error codes with description on controller exceptions.
