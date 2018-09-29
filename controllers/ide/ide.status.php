<?php
require_once __DIR__ . '/../../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && isset($_GET['user']) && isset($_GET['status']) && isset($_GET['hash'])) {

    $status = $_GET['status'];

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $api_url = CODECHEF_API_BASE_URL . "/ide/status?link=$status";

            $apiRequest = new CodechefApiCall($token, $api_url);
            $apiRequest->execute();
            $result = $apiRequest->getResult();
            $resultObj = json_decode($result);
            if ($resultObj->status == 'OK') {
                $data = $resultObj->result->data;
                \PhpUseful\EasyHeaders::json_header();
                echo json_encode($data);
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