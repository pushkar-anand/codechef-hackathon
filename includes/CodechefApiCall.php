<?php

require_once __DIR__ . '/../vendor/autoload.php';

class CodechefApiCall extends Curl
{
    private $result;
    private $token;

    function __construct(string $token, string $url)
    {
        parent::__construct();
        $this->token = $token;

        $header = array(
            "Accept: application/json",
            "Authorization: Bearer " . $token
        );

        parent::setUrl($url);
        parent::setHeaders($header);
        parent::setReturnTrue();
    }

    public function execute()
    {
        parent::execute();
        $this->result = parent::getResult();

        $obj = json_decode($this->result);
        if($obj->status == "error")
        {
            $errors = $obj->result->errors;
            $error = $errors[0];

        }
    }

    public function getResult()
    {
        return $this->result;
    }

    private function reIssueToken(){

    }
}