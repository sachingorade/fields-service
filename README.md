# fields-service
Fields data management service which allows -
1. CRUD operations for fields
1. R operations for field weather data

This service uses [agromonitoring.com](agromonitoring.com) for fetching weather data for the respective fields. 
## Features
Service offers management for following data -
1. Field
    1. CRUD operations for field
    1. Example field JSON
    ```json
    {
      "id": "a0f63e74-d7ef-4924-acb3-0e770ae9ec98",
      "name": "Potato field",
      "created": "2020-07-25T10:03:56.782Z",
      "updated": "",
      "countryCode": "DEU",
      "boundaries": {
        "id": "a0f63e74-d7ef-4924-acb3-0e960ae9ec98",
        "created": "2020-07-25T10:03:56.782Z",
        "updated": "",
        "geoJson": {
          "type": "Feature",
          "properties": {},
          "geometry": {
            "type": "Polygon",
            "coordinates": [
              [
                [-5.553604888914691, 33.88229680420605],
                [-5.5516736984239685, 33.88229680420605],
                [-5.5516736984239685, 33.88372189858022],
                [-5.555965232847882, 33.88390003370375],
                [-5.555965232847882, 33.88229680420605],
                [-5.553604888914691, 33.88229680420605]
              ]
            ]
          }
        }
      }
    }
    ```
2. Field weather
    1. Weather data for the fields
    1. Example weather data
    ```json
        {
          "timestamp": "1485705600",
          "temperature": 288.15,
          "humidity": 85,
          "temperatureMax": 289.16,
          "temperatureMin": 280.16
        }
    ```

## Building and running this project 
Following software need to build/run this project -
1. JDK 1.8 or later
1. Maven 3.2 or later
1. MongoDB 4.2.5 or later (local server or Docker instance)

### Build
1. Clone this repository
1. cd into `fields-service`
1. run `mvn clean install`

### Run
Once you build the project you can run this project by executing following command
from project's base directory -

`TODO add exact command to run project`