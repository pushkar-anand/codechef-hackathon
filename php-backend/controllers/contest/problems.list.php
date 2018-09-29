<?php
require_once __DIR__ . '/../../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && isset($_GET['user']) && isset($_GET['hash']) && isset($_GET['contest_code'])) {

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $contest_code = $_GET['contest_code'];
            $api_url = CODECHEF_API_BASE_URL . "/contests/$contest_code?fields=code,name,startDate,endDate,type,bannerFile,freezingTime,announcements,problemsList";

            $apiRequest = new CodechefApiCall($token, $api_url);
            $apiRequest->execute();
            $result = $apiRequest->getResult();
            $resultObj = json_decode($result);
            if ($resultObj->status == 'OK') {
                $code = $resultObj->result->data->code;
                if ($code == 9001) {
                    $content = $resultObj->result->data->content;
                    \PhpUseful\EasyHeaders::json_header();
                    echo json_encode($content);
                } else {
                    //handle codes
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
} else {
    \PhpUseful\EasyHeaders::bad_request();
}