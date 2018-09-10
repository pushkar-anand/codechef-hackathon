<?php

require_once __DIR__ . '/../vendor/autoload.php';

class CodechefApiCall extends Curl
{
    private $result;
    private $token;
    private $url;
    private $error = false;

    function __construct(string $token, string $url)
    {
        parent::__construct();
        $this->token = $token;
        $this->url = $url;

        $header = array(
            "Accept: application/json",
            "Authorization: Bearer " . $token
        );

        parent::setUrl($url);
        parent::setHeaders($header);
        parent::setReturnTrue();
    }

    function setPostJson(string $json)
    {
        parent::usePost(true);
        parent::setStringPostData($json);
    }

    public function execute()
    {
        parent::execute();
        $this->result = parent::getResult();

        $obj = json_decode($this->result);

        if ($obj->status !== "OK") {
            error_log('ERROR IN API CALL');
            error_log($this->result);

            $this->error = true;
            $errors = $obj->result->errors;
            $error = $errors[0];
            if ($error->code == "unauthorized") {
                $this->reIssueToken();
            } else {
                header("HTTP/1.1 401 Unauthorized");
                exit;
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
        return $db->fetchSingleField('users', 'refresh_token', 'codechef_token', $this->token);
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

        $result = json_decode($result);
        if ($result->status === 'OK') {
            $data = $result->result->data;
            $user = User::fetchUserFromAccessToken($this->token);

            $user->updateCodechefToken($data->access_token);
            $user->updateRefresfToken($data->refresh_token);
            $user->updateTokenExpire(date("Y-m-d H:i:s", strtotime("+3600 seconds")));

            $user->save();

            $apiRequest = new CodechefApiCall($data->access_token, $this->url);
            $apiRequest->execute();
            $this->result = $apiRequest->getResult();
        }
    }
}