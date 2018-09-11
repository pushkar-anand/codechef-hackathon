<?php
require_once __DIR__ . '/../../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && isset($_GET['user']) && isset($_GET['hash']) && isset($_GET['contest_code']) && isset($_GET['problem_code'])) {

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $contest_code = $_GET['contest_code'];
            $problem_code = $_GET['problem_code'];

            $api_url = CODECHEF_API_BASE_URL . "/contests/$contest_code/problems/$problem_code?fields=problemCode,problemName,languagesSupported,sourceSizeLimit,challengeType,maxTimeLimit,successfulSubmissions,body,tags";

            $apiRequest = new CodechefApiCall($token, $api_url);
            $apiRequest->execute();
            $result = $apiRequest->getResult();
            $resultObj = json_decode($result);
            if ($resultObj->status == 'OK') {
                $data = $resultObj->result->data;
                if ($data->code == 9001) {
                    \PhpUseful\EasyHeaders::json_header();
                    $content = $data->content;
                    echo json_encode($content);
                } else {
                    error_log("Invalid Code" . $data->message);
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
} else {
    \PhpUseful\EasyHeaders::bad_request();
}