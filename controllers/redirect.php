<?php
require_once __DIR__ . '/../vendor/autoload.php';

use PhpUseful\EasyHeaders;

if (isset($_GET['code']) && isset($_GET['state'])) {

    $token = $_GET['code'];
    $state = $_GET['state'];
    if ($state == $_SESSION['state']) {

        $headers = array(
            'content-Type: application/json',
        );

        $data = array(
            'grant_type' => "authorization_code",
            'code' => $token,
            'client_id' => CODECHEF_CLIENT_ID,
            'client_secret' => CODECHEF_CLIENT_SECRET,
            'redirect_uri' => REDIRECT_URI
        );

        $curl = new Curl();
        $curl->setUrl('https://api.codechef.com/oauth/token');
        $curl->setReturnTrue();
        $curl->usePost(true);
        $curl->setHeaders($headers);
        $curl->setStringPostData(json_encode($data));
        $curl->execute();

        $response = $curl->getResult();

        $response = json_decode($response);
        if ($response->status == 'OK') {
            $data = $response->result->data;

            $access_token = $data->access_token;
            $refresh_token = $data->refresh_token;
            $scope = $data->scope;

            $scopeArr = explode(' ', $scope);
            if (!in_array('submission', $scopeArr)) {
                require_once __DIR__ . '/../views/scope-error.html';
                exit();
            } else {

                $token_expire = date("Y-m-d H:i:s", strtotime("+3600 seconds"));

                $apiRequest = new CodechefApiCall($access_token, 'https://api.codechef.com/users/me');
                $apiRequest->execute();
                $response = json_decode($apiRequest->getResult());
                if ($response->status == 'OK') {
                    $data = $response->result->data;
                    $content = $data->content;
                    $username = $content->username;
                    $fullname = $content->fullname;

                    $register = new User($fullname, $username, $access_token, $refresh_token, $token_expire);
                    $login_token = $register->save();
                    if($login_token !== false)
                    {

                        $m = new Mustache_Engine(array(
                            'loader' => new Mustache_Loader_FilesystemLoader(__DIR__ . '/../views'),
                        ));
                        echo $m->render('final', array('name' => $fullname, 'login_token' => $login_token, 'codechef_handle' => $username));
                    }

                } else {
                    EasyHeaders::bad_request();
                }
            }

        } else {
            EasyHeaders::bad_request();
        }

    } else {
        EasyHeaders::bad_request();
    }

} else {
    EasyHeaders::not_found();
}