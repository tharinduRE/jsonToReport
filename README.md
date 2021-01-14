# JsonToReports

This REST microservice converts json data to a report generated build using jasper reports

This renders pdf and saved using json sent in to it & can be accessed from another REST call
## Motivation
Needed to create a microservice for one of my project which involves creating reports and storing them for later access

## Deploy
Deploy the microservice as docker image 

Build and deploy docker image using [Jib](https://github.com/GoogleContainerTools/jib) maven plugin

```shell
mvn jib:dockerBuild 
```

### *** NOTE 
Need to build the custom base image first using :
```shell
./base-image.sh
```
## Demo
### Request
```shell
curl --location --request POST 'http://localhost:8080/reports?name=StockItem' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Title of Report",
    "payload": [
        {
            "itemName": "test",
            "stockBookFolio": "121-1",
            "itemCapacity": 500.0,
            "totalQuantity": 5.0,
            "unitPrice": 1222.5,
            "measUnit": "g"
        }
    ]
}'

```
###Response 
```json
{
    "reportUrl": "/reports/report-14-01-2021-11-03PM.pdf"
}
```

Report can be then access from another rest call

```shell
curl --location --request GET 'http://localhost:8080/reports/report-14-01-2021-11-03PM.pdf'
```

