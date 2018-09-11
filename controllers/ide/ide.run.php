<?php
require_once __DIR__ . '/../../vendor/autoload.php';

$data = file_get_contents('php://input');
error_log("JSON_INPUT: $data");

if ($data) {
    $json = json_decode($data);

    if (isset($json->sourceCode) && isset($json->token) && isset($json->lang) && isset($json->user) && isset($json->input)) {
        $token = User::getCodechefToken($json->token, $json->user);

        if ($token !== false) {

            $api_url = CODECHEF_API_BASE_URL . '/ide/run';
            $post = array('sourceCode' => $json->sourceCode, 'language' => $json->lang, 'input' => $json->input);
            $api_req = new CodechefApiCall($token, $api_url);
            $api_req->setPostJson(json_encode($post));
            $api_req->execute();

            $response = $api_req->getResult();
            $responseObj = json_decode($response);
            error_log('IDE: ' . $response);
            var_dump($responseObj);

        } else {
            \PhpUseful\EasyHeaders::bad_request();
        }

    } else {
        \PhpUseful\EasyHeaders::bad_request();
    }
}