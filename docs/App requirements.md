App requirements
---

Important:

- has to be a generic software (= any university can use)
- anyone can sign up (you don’t have to be a student)

1. Users:
    - Login/Sign up
        - Username
        - Password
    - Account
        - Username
        - Role
            - Student (can reserve small rooms)
            - Employee (student rights + can reserve larger rooms)
            - Admin (can add/edit buildings, rooms, food menu, bike renting)
        - Your reservations
            - Displayed like a calendar
            - Cancel reservation (optional)
            - Bike reserved (Yes/No)

2. Rooms:
    - Code
    - Name
    - Capacity
    - Building
    - Availability
    - Pictures
    - Facilities (TV's, Whiteboards)
    - Reserving
        - Fixed time slot (let’s say 1 hour)
    - Filtering on (Campus level filtering + building level filtering)
        - Capacity
        - Availability
        - Facilities
    - Displayed as a list

3. Building:
    - Code
    - Name
    - Location
    - Opening hours
    - Bike station (Yes/No)
    - Food stores
    - Pictures

4. Renting bikes:
    - Number of available bikes on campus
        - Added by the admin
    - Can only rent from buildings where a bike station is located
    - Can rent multiple blocks of the fixed timeslots (so 3 times 1 hour slot)
    - That bike station has to have atleast one bike available
    - Rent for a fixed timeslot, can return earlier than the indicated end time
    - We don't have to deal with illegal stuff like returning later or not returning at all
    - Can return to any building with an attached bike station

5. Food:
    - Menu
        - List of food products
        - You can choose products from menu when ordering
        - Products have a price but we pay by cash
    - Can choose from any food store in the system to pick up
    - Can only choose from food stores in the same building as your reserved room if you want it to be delivered to your room
    - Food is unlimited and food is done at the given time
    - Products and food stores added by admin