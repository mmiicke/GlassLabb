<?php
/**
 * Created by PhpStorm.
 * User: Micke
 * Date: 2018-08-30
 * Time: 11:37
 */

error_reporting(E_ALL);
ini_set('display_errors',1);

class Model
{
/** @var instansvariabler */
    private $pdoConnection;
    private $pdoStatement;
//====================================================================
/** Metod för att hämta all data från databasen h16mikkn_blogg. Den innehåller alla blogginlägg
 */
public function getAllThoughts()
{
    try{
        if($this->pdoConnection==NULL){
            $this->pdoConnection=$this->getPDOConnection();
        }
        $sql='select * from h16mikkn_blogg order by id desc';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->execute();
        $contentArray = $this->pdoStatement->fetchAll(PDO::FETCH_ASSOC);
        $this->pdoConnection=NULL;
        return $contentArray;
}catch(PDOException $exp){
    echo'Något gick fel vid hämtning av data!', $exp->getMessage();
    $this->pdoConnection=NULL;
    die();
    }
}
//====================================================================
/** Metod för att lägga till i databasen h16mikkn_blogg  */
public function addThought()
{
    try{
        if($this->pdoConnection==NULL){
            $this->pdoConnection=$this->getPDOConnection();
        }
        $sql='INSERT INTO h16mikkn_blogg (rubrik, tanke, likes, dislikes, datum) VALUES (:rubriken, :tanken, 0, 0, CURRENT_TIMESTAMP)';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->bindParam(':rubriken',filter_var($_POST['rubrik'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->bindParam(':tanken',filter_var($_POST['tanke'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->execute();
        $this->pdoConnection=NULL;
    }catch(PDOException $exp){
    echo'Något gick fel när data skulle skrivas till databasen h16mikkn_blogg', $exp->getMessage();
    $this->pdoConnection=NULL;
    die();
    }
}
//===================================================================
/** Metod för att uppdatera antalet likes i databasen */
public function like()
{
    try{
        if($this->pdoConnection==NULL){
            $this->pdoConnection=$this->getPDOConnection();
        }
        $sql='update h16mikkn_blogg set likes = likes +1 where id = :idt';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->bindParam(':idt', filter_var($_POST['id'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->execute();
        $this->pdoConnection=NULL;
}catch(PDOException $exp){
    echo'Något gick fel vid uppdatering av likes i databasen', $exp->getMessage();
    $this->pdoConnection=NULL;
    die();
    }
}
//====================================================================
/** Metod för att uppdatera antalet dislikes i databasen  */
public function dislike()
{
    try{
        if($this->pdoConnection==NULL){
            $this->pdoConnection=$this->getPDOConnection();
        }
        $sql='update h16mikkn_blogg set dislikes = dislikes +1 where id = :idt';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->bindParam(':idt', filter_var($_POST['id'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->execute();
        $this->pdoConnection=NULL;
    }catch(PDOException $exp) {
        echo 'Något gick fel vid uppdatering av dislikes i databasen', $exp->getMessage();
        $this->pdoConnection = NULL;
        die();
    }
    }
//=====================================================================
/** metod för att hämta all data från databasen h16mikkn_comment, dvs alla kommentarer */
public function getAllComments()
{
    try {
        if ($this->pdoConnection == NULL) {
            $this->pdoConnection = $this->getPDOConnection();
        }
        $sql='select * from h16mikkn_comment order by id desc';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->execute();
        $commentArray = $this->pdoStatement->fetchAll(PDO::FETCH_ASSOC);
        $this->pdoConnection=NULL;
        return $commentArray;

    } catch (PDOException $exp) {
        echo 'Något gick fel vid uppdatering av dislikes i databasen', $exp->getMessage();
        $this->pdoConnection = NULL;
        die();
    }
}
//============================================================================
/** Metod för att lägga tidd data i databasen h16mikkn_comment, lägga till nya kommentarer */
public function addComment()
{
    try {
        if ($this->pdoConnection == NULL) {
            $this->pdoConnection = $this->getPDOConnection();
        }
        $sql='INSERT INTO h16mikkn_comment (tankeid, kommentar, datum) VALUES (:tankeidt, :kommentaren, CURRENT_TIMESTAMP)';
        $this->pdoStatement=$this->pdoConnection->prepare($sql);
        $this->pdoStatement->bindParam(':tankeidt',filter_var($_POST['tankeid'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->bindParam(':kommentaren',filter_var($_POST['kommentar'],FILTER_SANITIZE_STRING));
        $this->pdoStatement->execute();
        $this->pdoConnection=NULL;

    } catch (PDOException $exp) {
        echo 'Något gick fel vid uppdatering av dislikes i databasen', $exp->getMessage();
        $this->pdoConnection = NULL;
        die();
    }
}
//====================================================================
//Funktion som kopplar upp mot databasen
    private function getPDOConnection(){
        //funktion för metoden att skapa en uppkoppling
        $dsn='mysql:host=utb-mysql.du.se;dbname=db30';

        $user='db30';
        $pass='FJJAcyMU';

        $options=array(PDO::MYSQL_ATTR_INIT_COMMAND=>"SET NAMES utf8");

        try{
            //connection to the database
            $pdoConnection=new PDO($dsn, $user, $pass, $options);
            return $pdoConnection;
        }
        catch(PDOException $pdoexp){
            $pdoConnection=NULL;//close connection;
            echo'DB error', $pdoexp->getMessage();
            die();
        }//End catch
    }//End getPDOConnection
}