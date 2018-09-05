<?php

require_once __DIR__ . '/../vendor/autoload.php';

class CodechefApiCall extends Curl
{
    private $result;
    private $token;
    private $error = false;
    private $user;

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
        if ($obj->status == "error") {
            $this->error = true;
            $errors = $obj->result->errors;
            $error = $errors[0];
            if ($error->code == "unauthorized") {
                $this->reIssueToken();
            } else {
                //return no access;
            }
        }
    }

    public function getResult()
    {
        return $this->result;
    }

    private function getRefreshToken()
    {
        $db = new DB();
        return $db->fetchSingle('users', 'refresh_token', 'codechef_token', $this->token);
    }

    private function reIssueToken()
    {
        $r_token = $this->getRefreshToken();

        error_log("Refresh Token: $r_token");

        $headers = array(
            'content-Type: application/json',
        );

        $data = array(
            'grant_type' => "refresh_token",
            'refresh_token' => $r_token,
            'client_id' => CODECHEF_CLIENT_ID,
            'client_secret' => CODECHEF_CLIENT_SECRET
        );

        $curl = new Curl();
        $curl->setUrl('https://api.codechef.com/oauth/token');
        $curl->setReturnTrue();
        $curl->usePost(true);
        $curl->setHeaders($headers);
        $curl->setStringPostData(json_encode($data));
        $curl->execute();

        $result = $curl->getResult();
        error_log("Reissue: $result");
        var_dump($result);

    }
}