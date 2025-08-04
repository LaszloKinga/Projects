# IDDE laborfeladatok
(
Labor1:
felület:
    desktopos alkalmazás
mem és jdbc
futtatás:
    Main



Labor2:
felület:
    desktopos alkalmazás
mem és jdbc
futtatás:
    Main
)


Labor3: GET http://localhost:8080/lkim2156-web/webshops
felület:
    desktopos alkalmazás
    web felület
mem és jdbc
futtatás:
    desktopos ->
        cd ./lkim2156-desktop 
        gardle run 
    webeset -> gradle deploy 
               catalina run
adatbázis: idde


(
Labor4:
felület:
    desktopos alkalmazás
    web felület
mem és jdbc
futtatás:
    gradle deploy
    catalina run
adatbázis: iddelab4
)


Labor5: GET http://localhost:8080/webshops
        GET http://localhost:8080/stores
        GET http://localhost:8080/api/stores/1/webshops
felület:
    web felület
mem és jdbc és jpa
futtatás: 
    gradle bootRun
adatbázis: iddelab4
           iddelab4jpa