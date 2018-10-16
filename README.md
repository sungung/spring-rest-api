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
```
$ curl -i -X POST -H 'Content-Type: application/json' -d '{"code":4,"name":"Erase","origin":"China","price":10.0}' http://localhost:8080/products/a

HTTP/1.1 202
Location: http://localhost:8080/queue/4
Content-Length: 0
Date: Fri, 16 Mar 2018 01:31:11 GMT
```

* Return HTTP 303(See Other) after the operation completes. Location header gives the URI of the new resource.
```
$ curl -i http://localhost:8080/queue/4

HTTP/1.1 303
Location: http://localhost:8080/products/4
Content-Length: 0
Date: Fri, 16 Mar 2018 01:28:35 GMT
```

### Pagination

* Hypermedia support
* Using a PagedResourcesAssembler
```
$ curl -i http://localhost:8080/products
HTTP/1.1 200
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 16 Oct 2018 01:08:31 GMT

{"_embedded":{"productList":[{"code":0,"name":"prod0","origin":"name0","price":0.0},{"code":1,"name":"prod1","origin":"name1","price":10.0},{"code":2,"name":"prod2","origin":"name2","price":20.0},{"code":3,"name":"prod3","origin":"name3","price":30.0},{"code":4,"name":"prod4","origin":"name4","price":40.0},{"code":5,"name":"prod5","origin":"name5","price":50.0},{"code":6,"name":"prod6","origin":"name6","price":60.0},{"code":7,"name":"prod7","origin":"name7","price":70.0},{"code":8,"name":"prod8","origin":"name8","price":80.0},{"code":9,"name":"prod9","origin":"name9","price":90.0},{"code":10,"name":"prod10","origin":"name10","price":100.0},{"code":11,"name":"prod11","origin":"name11","price":110.0},{"code":12,"name":"prod12","origin":"name12","price":120.0},{"code":13,"name":"prod13","origin":"name13","price":130.0},{"code":14,"name":"prod14","origin":"name14","price":140.0},{"code":15,"name":"prod15","origin":"name15","price":150.0},{"code":16,"name":"prod16","origin":"name16","price":160.0},{"code":17,"name":"prod17","origin":"name17","price":170.0},{"code":18,"name":"prod18","origin":"name18","price":180.0},{"code":19,"name":"prod19","origin":"name19","price":190.0}]},"_links":{"first":{"href":"http://localhost:8080/products?page=0&size=20"},"self":{"href":"http://localhost:8080/products?page=0&size=20"},"next":{"href":"http://localhost:8080/products?page=1&size=20"},"last":{"href":"http://localhost:8080/products?page=4&size=20"}},"page":{"size":20,"totalElements":100,"totalPages":5,"number":0}}
```

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

### Running in docker
* Create Dockerfile to specify the layers of an image
* Build a docker image with maven
```
	<properties>
		<docker.image.prefix>sungung</docker.image.prefix>
	</properties>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>dockerfile-maven-plugin</artifactId>
				<version>1.3.6</version>
				<configuration>
					<repository>${docker.image.prefix}/${project.artifactId}</repository>
					<buildArgs>
						<JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
					</buildArgs>
				</configuration>
			</plugin>
		</plugins>
	</build>
```
* Run 'mvn clean install dockerfile:build'
* Run image 'docker run -p 8080:8080 -t sungung/spring-rest-api'



