## Hotel API

- POST `hotel/insert`
    - Creates or updates a hotel.
    - Form Parameters
        - name (required)
    - Responses
        - 200 with a JSON.

- POST `hotel/delete`
    - Deletes a hotel.
    - Form Parameters
        - name (required)
        - airConditioning (required)
        - rating (required)
        - description (required)
        - address (required)
    - Responses
        - 200 on successful deletion.
        - 400 when the hotel is not found.

- GET `hotel/get`
    - Retrieves a hotel object.
    - Query Parameters
        - name (required)
    - Responses
        - 200 with a JSON on success.
        - 404 when the hotel is not found.

- GET `hotel/pretty-get`
    - Returns a web page for the hotel.
    - Query Parameters
        - name (required)
    - Responses
        - 200 with HTML on sucess.
        - 404 when the hotel is not found.

- POST `hotel/email`
    - Sends an email of the web page.
    - Form Parameters
        - name (required)
        - emailAddress (required)
    - Responses
        - 200 on successful email.
        - 400 when the hotel is not found.

## Room API

- POST `room/insert`
    - Creates or updates a room.
    - Form Parameters
        - room (required)
        - floor (required)
        - hotelName (required)
    - Responses
        - 200 with a JSON.
        - 400 when the hotel is not found.

- POST `room/delete`
    - Deletes a hotel.
    - Form Parameters
        - room (required)
    - Responses
        - 200 on successful deletion.
        - 400 when the room is not found.

- GET `room/get`
    - Retrieves a room object.
    - Query Parameters
        - room (required)
    - Responses
        - 200 with a JSON on success.
        - 404 when the room is not found.