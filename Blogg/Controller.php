<?php
/**
 * Created by PhpStorm.
 * User: Micke
 * Date: 2018-08-30
 * Time: 11:37
 */
require_once ('./Model.php'); //require once används istället för include_once eftersom det annars kan bli konflikter när man vill bygga ut applikationen och klassen inkluderas i fler steg
require_once ('./View.php');

class Controller
{
/** * Controllern bli i denna app väldigt enkel och fungerar som en länk
 * mellan modellen och vyn för att bestämma vilken data som ska visas
 */

    /**instansdata */
    private $model;
    private $view;
//======================================================================================================================
    function __construct() { //konstruktor
        $this->model = new Model(); //skapar upp ett objekt av Model-klassen
        $this->view = new View(); //skapar upp ett objekt av View-klassen
    }

    public function showAllThoughts(){ //metod anropas via URL för att visa samtliga objekt i databasen
        $this->view->display('./template1.html');
    }
//======================================================================================================================
    public function getFullJsonArray(){
        $fullArray = $this->model->getAllThoughts();
        echo json_encode($fullArray);
    }
//======================================================================================================================
    public function addThought(){
        $this->model->addThought();
    }
//======================================================================================================================
    public function like(){
        $this->model->like();
    }
//======================================================================================================================
    public function dislike(){
        $this->model->dislike();
    }
//======================================================================================================================
    public function getFullJsonCommentsArray(){
        $fullArray = $this->model->getAllComments();
        echo json_encode($fullArray);
    }
//======================================================================================================================
    public function addComment(){
        $this->model->addComment();
    }
//======================================================================================================================

}