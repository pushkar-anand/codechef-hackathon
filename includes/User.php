<?php
require_once __DIR__ . '/../vendor/autoload.php';

use PhpUseful\Functions;

class User
{

    private $name;
    private $codechef_handle;
    private $login_token;
    private $codechef_token;
    private $token_expire;
    private $refresh_token;
    private $DB;


    public function __construct(string $name, string $codechef_handle, string $codechef_token, string $refresh_token, string $token_expire)
    {
        $this->DB = new DB();

        $this->name = Functions::escape_input($name);

        $this->codechef_handle = Functions::escape_input($codechef_handle);

        $this->login_token = Functions::random_string(20);
        $this->codechef_token = $codechef_token;
        $this->token_expire = $token_expire;
        $this->refresh_token = $refresh_token;

    }

    public static function User()
    {

    }

    public function save()
    {
        $conn = $this->DB->getConnection();

        $query = "INSERT INTO users(name, codechef_username, codechef_token, login_token, refresh_token, token_expire) VALUES (?, ?, ?, ?, ?, ?)";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("ssssss", $this->name, $this->codechef_handle, $this->codechef_token, $this->login_token, $this->refresh_token, $this->token_expire);
        if ($stmt->execute()) {
            return $this->login_token;
        } else {
            error_log($conn->error);
            return false;
        }
    }


}