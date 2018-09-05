<?php
require_once __DIR__ . '/../../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && isset($_GET['user']) && isset($_GET['hash']) ) {

    $offset = (isset($_GET['offset'])) ? $_GET['offset'] : 1;

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $api_url = CODECHEF_API_BASE_URL . "/problems/medium?fields=problemCode,problemName,successfulSubmissions,accuracy&offset=$offset&limit=10";

            $problems = practice_request($token, $api_url);
            \PhpUseful\EasyHeaders::json_header();
            echo $problems;

        } else {
            \PhpUseful\EasyHeaders::bad_request();
        }
    } else {
        \PhpUseful\EasyHeaders::bad_request();
    }
} else {
    \PhpUseful\EasyHeaders::bad_request();
}