# Running the Project

These instructions assume that this project will be run on Linux. For running on Windows or Mac they would need to be adapted slightly.

Download and install the latest 1.3 SBT release from the SBT website (https://www.scala-sbt.org/download.html). SBT is used to build and run this project.

Ensure that docker is installed and working

Set your weather api key as an environment variable
```
export WEATHER_API_KEY=xxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
Execute the runapi script in this directory

Exercise the API by using the provided bash scripts. e.g.

```
./weather Swindon
./insertissue
```