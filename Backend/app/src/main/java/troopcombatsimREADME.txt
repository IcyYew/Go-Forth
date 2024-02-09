As of 2/9/2024 this application should be a working player declaring, troop manipulating and battle simulator

To use this you can simply to go to Postman, start a new HTTP project and follow the instructions in the PlayerController



!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
IMPORTANT ---> follow the instructions in the PlayerController
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

Below is storage for setting up a basic battle simulation between two players:

localhost:8080/players/new/
    click "Body" underneath URL and click raw
    in here simply type a username, ex. Michael

        Michael
        playerID = 1

localhost:8080/players/new/
    click "Body" underneath URL and click raw
    in here simply type a username

        Michael2
        playerID = 2


Below is the URLs to provide players with troops, you can play around with the intgers on the end of each URL to see different battle results
v !! Important !! v
Pass these urls independently of one another, this app currently isnt capable of doing batch requests of this type
^ !! Important !! ^

localhost:8080/players/addtroops/1?troopType=ARCHER&recruited=100
localhost:8080/players/addtroops/1?troopType=MAGE&recruited=100
localhost:8080/players/addtroops/1?troopType=WARRIOR&recruited=100
localhost:8080/players/addtroops/1?troopType=CAVALRY&recruited=100

localhost:8080/players/addtroops/2?troopType=ARCHER&recruited=100
localhost:8080/players/addtroops/2?troopType=MAGE&recruited=100
localhost:8080/players/addtroops/2?troopType=WARRIOR&recruited=100
localhost:8080/players/addtroops/2?troopType=CAVALRY&recruited=100

After both players have the troops that you want, simply send the request:
localhost:8080/players/fight/1/2



RAW URLs, no commentary:
localhost:8080/players/new/
localhost:8080/players/new/
localhost:8080/players/addtroops/1?troopType=ARCHER&recruited=100
localhost:8080/players/addtroops/1?troopType=MAGE&recruited=100
localhost:8080/players/addtroops/1?troopType=WARRIOR&recruited=100
localhost:8080/players/addtroops/1?troopType=CAVALRY&recruited=100
localhost:8080/players/addtroops/2?troopType=ARCHER&recruited=100
localhost:8080/players/addtroops/2?troopType=MAGE&recruited=100
localhost:8080/players/addtroops/2?troopType=WARRIOR&recruited=100
localhost:8080/players/addtroops/2?troopType=CAVALRY&recruited=100
localhost:8080/players/fight/1/2