<?php
/**
 * Created by PhpStorm.
 * User: Micke
 * Date: 2018-08-30
 * Time: 12:49
 */


/*Kod inspirerad av föreläsningar av Pär Eriksson*/

error_reporting(E_ERROR); // Felhantering, tas bort i produktionsmiljö av säkerhetsskäl
ini_set('display_errors', 1);

class View
{
    /*
     * Denna enda metod tar emot data från controllern och visar rätt html-fil*/

    public function display($template) { //metoden anropas via controllern för att visa den html-fil man vill

        if (file_exists($template)) {
            include_once($template);
        } else {
            throw new Exception('Template finns ej');
        }
    }
}