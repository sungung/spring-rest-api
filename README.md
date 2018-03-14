# REST best practice


## Design

### Versioning 
You cannot control client logic when host resource or the relationship between resources are changed by new requirements. In that context, version will make safe to change host resources without client damage.

* Non versioning

* URI versioning
```
$ curl -i http://localhost:8080/v1/customers/1

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 05:30:27 GMT

{"firstName":"John","lastName":"Smith"}
```

* Query string versioning

* Header versioning
```
$ curl -i -H "apiVersion: v1" http://localhost:8080/header/customers/1

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:21:34 GMT

{"firstName":"John","lastName":"Smith"}
```
```
$ curl -i -H "apiVersion: v2" http://localhost:8080/header/customers/1

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:21:45 GMT

{"firstName":"John","lastName":"Smith","email":"john@company.com"}
```
```
$ curl -i -H "apiVersion: v3" http://localhost:8080/header/customers/1

HTTP/1.1 415
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:21:59 GMT

{"message":"Unsupported version"}
```
* Media type versioning
```
$ curl -i -H "Content-Type: application/vnd.api-v1.0+json" http://localhost:8080/customers/1

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:18:09 GMT

{"firstName":"John","lastName":"Smith"}
```
```
$ curl -i -H "Content-Type: application/vnd.api-v1.1+json" http://localhost:8080/customers/1

HTTP/1.1 415
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:18:16 GMT

{"message":"Unsupported version"}
```

### Media Type
* Content-Type header - a request or response specifies the format of the representation.
* Accept header - a client request can include. 

```
$ curl -i -H "Accept: application/xml" http://localhost:8080/customers/1

HTTP/1.1 200
Content-Type: application/xml
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 05:32:56 GMT



<?xml version="1.0" encoding="UTF-8" standalone="yes"?><customer><id>1</id><firstName>John</firstName><lastName>Smith</lastName><email>john@company.com</email></customer>
```
```
$ curl -i -H "Accept: application/json" http://localhost:8080/customers/1

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 05:33:42 GMT

{"id":1,"firstName":"John","lastName":"Smith","email":"john@company.com"}
```

### Asynchronous operations
* Return HTTP 202(Accepted) to indicate the request was accepted for processing but is not completed.
* Return HTTP 303(See Other) after the operation completes. Location header gives the URI of the new resource.

### Pagination

### Partial responses for large binary resources
* Implement HTTP HEAD request for these resource to expose the size of resource.
* Client request will include Range header to fetch the resource, and server will return a partial response by returning HTTP 206 code.

### HATEOAS - Hypertext as the Engine of Application State 

### Swagger
Generate client documentation from API contract
```
$ curl -i http://localhost:8080/v2/api-docs

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 22:33:06 GMT

{"swagger":"2.0","info":{"description":"Api Documentation","version":"1.0","title":"Api Documentation","termsOfService":"urn:tos","con
```
To see Swagger UI, open 'http://localhost:8080/swagger-ui.html' in the browser.

## Implementation

### Exception Handling
* Distinguish between client side errors(the HTTP 4xx status) and service side errors(the HTTP 5xx status)

```
$ curl -i http://localhost:8080/exception

HTTP/1.1 409
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 14 Mar 2018 04:44:59 GMT

{"message":"Cannot divided by zero"}

```

### Optimising client

* Client caching
Cache-Control header

* Client caching and optimistic concurrency
ETag header - tip) for security reasons, not allow sensitive data

* Asynchronous operation for large resources or long running requests

### Maintaining response, scalability and availability




