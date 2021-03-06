﻿# Campus Life

> A campus management system for room reservations, food orders and bike renting.


![](docs/Pictures/Main.png)


## Description of project

Campus Life is a generic campus management software that can make the life of both students and teachers easier.
Some of its features are:
* Reserve rooms on campus
  * With a permission system so only teachers can reserve lecture halls
* Reserve a bike from a bike station
* Order food from a restaurant on campus
  * Can be delivered if you have reserved a room, otherwise it is for takeout
* A detailed overview of all your reservations and food orders
  * Custom events can also be added
* A fully functional admin page where:
  * Buildings, rooms and restaurants can be added and removed.
  * Building's opening times and picture can be changed
  * Restrictions for rooms can be added
  * Restaurant menu's and pictures can be updated
  * Bike stations can be added, or the number of available bikes can be updated
* And many other things

### More pictures
<details>
    <summary>Pictures</summary> 
    
   ![](docs/Pictures/Building.png)
    ![](docs/Pictures/Rooms.png)
    ![](docs/Pictures/ReserveRoom.png)
    ![](docs/Pictures/EditBuilding.png)
    ![](docs/Pictures/UserPermissions.png)
</details>

## How to run it
1. Add a valid application.properties file under [server/src/main/resources](server/src/main/resources) to make sure the server can connect to a database. There is an [example file](server/src/main/resources/application.properties.example) for ease of use.
2. Run the server by starting [CampusLife.java](server/src/main/java/nl/tudelft/oopp/demo/CampusLife.java).
3. (Optional) Change the client's SERVER_URL in [ServerCommunication.java](client/src/main/java/nl/tudelft/oopp/demo/communication/ServerCommunication.java) to the IP of your server.
4. Run the client by starting [MainApp.java](client/src/main/java/nl/tudelft/oopp/demo/MainApp.java).


## Group members

| 📸 | Name | Email |
|---|---|---|
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2540/avatar.png?width=400) | Kriss Tesink | K.A.Tesink@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2659/avatar.png?width=400) | Wouter Büthker | w.w.buthker@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=OOPP&length=4&size=200&color=DDD&background=777&font-size=0.325) | Tsin Yu Huang | T.Y.Huang@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2523/avatar.png?width=400) | Ana Băltărețu | A.Baltaretu@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2570/avatar.png?width=400) | Hendy Liang | H.Liang-3@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2487/avatar.png?width=400) | Willem Stuijt | W.L.StuijtGiacaman@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/2556/avatar.png?width=400) | Andra-Diana Maglas | A.D.Maglas@student.tudelft.nl |



Note: This is a copy of the private TU Delft Gitlab repository, therefore merge requests and issue boards are unfortunately unavailable

## Copyright / License
[MIT](LICENSE)
