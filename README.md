# AccelByte GDPR Java SDK

GDPR SDK for integrating Java services with AGS (AccelByte Gaming Services) GDPR service.

This GDPR SDK could be used by participant services to integrate into AGS GDPR workflow.
There are 3 GDPR workflow that this GDPR SDK supported:
1. Right to data portability
2. Right to erasure (right to be forgotten)
3. Right to restrict processing

The participant services will hook their _**concrete GDPRHandler implementation**_ (that contains 3 functionalities above) into this GDPR SDK.

Under the hood, this GDPR SDK was using gRPC protocol for communication with AGS GDPR service:

```
+---------------+              +-------------------+
|     AGS       |     gRPC     | Your Java Service |
| GDPR Service -+------------->|   (gRPC Server)   |
| (gRPC Client) |              |                   |
+---------------+              +-------------------+
```

## Requirements

Minimum Java version: Java 1.8

## Setup

### Gradle
```shell
dependencies {
  implementation "net.accelbyte.sdk:gdpr-sdk:1.0.0"
}
```

### Maven
```shell
<dependency>
  <groupId>net.accelbyte.sdk</groupId>
  <artifactId>gdpr-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

[GDPR SDK jar downloads](https://search.maven.org/artifact/net.accelbyte.sdk/gdpr-sdk/1.0.0/jar) are available from Maven Central.

## Usage

### Create Concrete Implementation of [GDPRHandler](src/main/java/net/accelbyte/gdpr/sdk/GDPRHandler.java) interface

Create new class to implement [GDPRHandler](src/main/java/net/accelbyte/gdpr/sdk/GDPRHandler.java) interface:
```java
public class MyGDPRHandler implements GDPRHandler {
    @Override
    public DataGenerationResult ProcessDataGeneration(String namespace, String userId, boolean isPublisherNamespace) {
        log.info("collecting user data...");

        // your implementation here...
        
        // example result
        DataGenerationResult result = new DataGenerationResult();
        result.setData("module1", "{\"data\":\"lorem ipsum dolor sit amet\"}".getBytes());
        result.setData("module2", "{\"key1\":\"lorem ipsum\",\"key2\":\"dolor sit amet\"}".getBytes());
        return result;
    }

    @Override
    public void ProcessDataDeletion(String namespace, String userId, boolean isPublisherNamespace) {
        log.info("deleting user data...");
        
        // your implementation here...
    }
    
    @Override
    public void ProcessDataRestriction(String namespace, String userId, boolean restrict, boolean isPublisherNamespace) {
        log.info("restrict processing user data...");

        // your implementation here...
    }
}
```

### Initialize gRPC Server with [GDPRService](src/main/java/net/accelbyte/gdpr/sdk/GDPRService.java) class

In your main implementation, hook your Concrete Implementation of [GDPRHandler](src/main/java/net/accelbyte/gdpr/sdk/GDPRHandler.java) into [GDPRService](src/main/java/net/accelbyte/gdpr/sdk/GDPRService.java) class:
```java
GDPRHandler myGDPRHandler = new MyGDPRHandler();
GDPRService.SetHandler(myGDPRHandler);
```

Start gRPC Server with [GDPRService](src/main/java/net/accelbyte/gdpr/sdk/GDPRService.java) class:
```java
ServerBuilder<?> serverBuilder = Grpc.newServerBuilderForPort(8081, InsecureServerCredentials.create());
serverBuilder.addService(new GDPRService()).addService(ProtoReflectionService.newInstance());

Server server = serverBuilder.build().start();
server.awaitTermination();
```
