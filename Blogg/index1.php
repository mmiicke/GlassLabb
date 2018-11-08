<?php
/**
 * Created by PhpStorm.
 * User: Micke
 * Date: 2018-08-30
 * Time: 12:47
 */
error_reporting(E_ALL); // Felhantering, ska tas bort i produktionsmiljö av säkerhetsskäl
ini_set('display_errors', 1);
//===============================================================================================
require_once ('./Controller.php');
//=================================================================================================
$queryArray = explode("/", $_SERVER['QUERY_STRING']); //URL:en delas upp till en array med hjälp av en delimiter som i detta fall är slash-tecknet
//================================================================================================
if (count($queryArray) === 2) {  //Om det finns två steg (controller + metod) i URL:en körs denna if
    $cont = new $queryArray[0];
    $func = $queryArray[1];

    $cont->$func();

}
else if (count($queryArray) === 3) { //Om det finns tre steg (controller + metod + parameter) i URL:en körs denna if. Hade ej varit nödvändig för denna applikation med facit i hand.
    $cont = new $queryArray[0];
    $func = $queryArray[1];

    $cont->$func($queryArray[2]);
}
else {
    echo "Sidan finns ej";
}