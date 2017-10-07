# Application: Flower Shop
A console based Shopping application that processes customer order on a per bundle basis.

## Problem Statement
A shop started selling Products on a per bundle basis. Example: if product R12 has 2 bundles 
5 @ $6.99 and 10 @ $12.99 and a customer places and order of 15 R12 he / she will be charged 1 x $12.99 and 1 x $6.99

## Solution
Products and Bundles will be configurable for the application. Application will run with a input 
file containing customer order information (product code and quantity). Application will process
this information and try to match with pre-loaded Bundle information. Application will create 
Order and display order information in the console.

### Assumptions:
 * Products don't have price associated with them, instead prices are based on per bundle basis
 * if the ordered quantity (in the order input file) doesn't match any combination of
 existing bundles then there won't be any item for that product in the order (i.e. no
 partial quantity)  

### Domain Models
 * **Product** can be any product. In this context product is a flower
 * **Bundle** multiple unit of the Product that can be sold
 * **ProductCatalog** association for Product and Bundle(s)
 * **Shopping Cart:** user puts order via customer order file see [order-input.txt](order-input.txt) as an example.
Shopping Cart holds total quantity of the items (irrespective of Bundles) as ShoppingCartItem(s)
```
item 1: 10 R12
item 2: 15 L09
```
 * **Order** Customer Order 
 * ***OrderItem*** each OrderItem represents a purchased Product and associated quantities. OrderItem contains
 quantities on a per Bundle basis
```
10 R12 $12.99
  1 x 10 $12.99
15 L09 $41.90
  1 x 9 $24.95
  1 x 6 $16.95
```
 
 
### Application Configuration

* Catalog: see [catalog.yaml](catalog.yaml) for example of defining products and bundles
```
catalogItems:
  - product:
      name: Roses
      code: R12
    bundles:
      - quantity: 5
        price: 6.99
      - quantity: 10
        price: 12.99
  - product:
      name: Lilies
      code: L09
    bundles:
      - quantity: 3
        price: 9.95
      - quantity: 6
        price: 16.95
``` 
* Default Configuration Files: defined in [application.properties](src/main/resources/application.properties)
```
catalog.file=catalog.yaml
order.file=order-input.txt
```
(project root folder). Location can be overridden with the below command line arguments during 
running the application:
```
--order.file=/tmp/order-input.txt --catalog.file=/tmp/catalog.yaml
```   

## Technology (language, build tool and framework)
Springboot framework is used as it is widely used in the industry and provides opinionated features and
dependency injection. Also this application can easily be converted to web application. 
 
```
java (8 or above)
gradle (4.x)
SpringBoot (1.5.7-RELEASE)
```
 
## Run Tests:
```
gradle --info clean test
```
test generates report in ```build/reports/tests/test```

### Build Application:
```
gradle --info clean build
```
an executable jar will be generated in ```build/libs/flower-shop-0.1.0.jar```

### Run Application:
(with default catalog.yaml and order-input.txt files provided with this distribution) 
```
java -jar build/libs/flower-shop-0.1.0.jar
```
or 

```
java -jar build/libs/flower-shop-0.1.0.jar --order.file=/tmp/order-input.txt --catalog.file=/tmp/catalog.yaml
```
(you can create your own input files and use above command line arguments for your file locations)

### Run in Docker:
If you don't want to install jdk and gradle and have docker installed you can build and run 
the application in docker container as well. 

#### build:
```
docker build -t flower-app .
```

#### Run:
```
docker run -it flower-app:latest
```
(with default files provided)
or
```
docker run -it -v "$PWD"/testFiles:/testFiles flower-app:latest java --order.file=/testFiles/order-input1.txt --catalog.file=/testFiles/catalog1.yaml 
```
(custom files created in 'testFiles' directory of project root)

#### log file
log file will be generated in logs/application.log

### Troubleshoot
* Wrong Order Input File: for below type error (in console) check the name and location of the order file:
```
Couldn't read order from file. Error: java.nio.file.NoSuchFileException: /tmp/order-input5.txt
```
* Invalid line in Order Input File: if the line doesn't contain quantity and product code only then look for 
error message like below:
```
Couldn't convert input order line : 15 22 22 L09. Skipping the line.
```
* Wrong catalog configuration file: look for error message like below:
```
Error: Missing or incorrect catalog file. (/tmp/catalog3.yaml (No such file or directory))
```

