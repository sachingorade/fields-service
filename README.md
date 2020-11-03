# fields-service
Fields data management service which allows -
1. CRUD operations for fields
1. R operations for field weather data

This service uses [agromonitoring.com](agromonitoring.com) for fetching weather data for the respective fields. 
## Features
Service offers management for following data -
### Field
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
### Field weather
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

## Exposed API
1. Create a new field -
    ```http
    POST /fields
    ```
    Request body should contain Field JSON as the given example above
1. Fetch all previously created fields -
    ```http request
    GET /fields
    ```
1. Update the field details -
    ```http request
    PUT /fields/{FIELD_ID}

    Content-Type: application/json
       
    {
        "id": "7d8a6a79-d331-4bf6-8e22-6ad2239969ac",
        "name": "Potato field with coriander",
        "created": "2020-07-25T10:03:56.782",
        "updated": null,
        "countryCode": "DEU"
    }
    ```
1. Delete field
    ```http request
    DELETE /fields/{FIELD_ID}
    ```
1. Get field weather information of last 7 days for specified field
    ```http request
    GET /fields/{FIELD_ID}/weather
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

`java -DAM_APP_ID="AGROMONITORING_APP_ID" -jar  fields-service-0.0.1-SNAPSHOT.jar`

## Using docker-compose
Navigate to root directory of the project and execute following command,

`docker-compose up`

## Deploying on Kubernetes
Navigate to `k8s` directory and execute following command,

`kubectl create -f .`

After Kubernetes deploys all the containers, you can access application at `http://NODE_IP:30080`