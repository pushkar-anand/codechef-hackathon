<?php
require_once __DIR__ . '/../vendor/autoload.php';

class DB{

    private $conn;
    private $error = false;

    public function __construct()
    {
        $this->conn = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_NAME);

        if($this->conn->error)
        {
            $this->error = true;
            error_log('DATABASE:' . $this->conn->error);
            die('Database Error..');
        }
    }

    public function getConnection()
    {
        return $this->conn;
    }

    public function getResultCount(string $table, string $where_field, string $match)
    {
        $query = "SELECT * FROM $table WHERE $where_field = ? ";
        $stmt = $this->conn->prepare($query);
        $stmt->bind_param('s', $match);
        $stmt->execute();
        $stmt->store_result();

        $num_of_rows = $stmt->num_rows;

        $stmt->free_result();
        $stmt->close();

        return $num_of_rows;
    }


    public function insert(string $table, array $fields, string $params, string ...$vals){

        $fieldsSTR = implode(",", $fields);

        $c = str_repeat("?, ", count($fields));
        $placeholders = rtrim($c, ", ");

        $query = "INSERT INTO $table($fieldsSTR) VALUES($placeholders)";

        $stmt = $this->conn->prepare($query);
        $stmt->bind_param($params,$vals);
        $stmt->execute();
    }

}