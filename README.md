# web-parser

## How it works
* Application parses three categories: Elektronika, Zdorowie, Dom i ogrod
* At the first stage application collects all the links with discount items of one of the category
* Then information about each item is collected
* All the data of discount items is written to `items.csv` file located here `src/main/resources/items.csv`
* Final `items.csv` looks like:
![image](docs/Screenshot_1.png)

## Possible ways of application optimization
* Think about multithreading implementation to shorten time of app running
* Format output to `items.csv` file in accordance to certain logic