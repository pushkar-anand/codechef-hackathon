<?php
require_once __DIR__ . '/../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && $_GET['user'] && $_GET['hash']) {

    $offset = (isset($_GET['offset'])) ? $_GET['offset'] : 1;

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $api_url = CODECHEF_API_BASE_URL . "/problems/school?fields=problemCode,problemName,successfulSubmissions,accuracy&offset=$offset&limit=10";

            $apiRequest = new CodechefApiCall($token, $api_url);
            $apiRequest->execute();
            $result = $apiRequest->getResult();
            $resultObj = json_decode($result);

            if ($resultObj->status == 'OK') {
                $problems = $resultObj->result->data->content;
                \PhpUseful\EasyHeaders::json_header();
                echo json_encode($problems);

            } else {
                \PhpUseful\EasyHeaders::bad_request();
            }

        } else {
            \PhpUseful\EasyHeaders::bad_request();
        }
    } else {
        \PhpUseful\EasyHeaders::bad_request();
    }
} else {
    \PhpUseful\EasyHeaders::bad_request();
}