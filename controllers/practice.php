<?php
require_once __DIR__ . '/../vendor/autoload.php';

use PhpUseful\Functions;

if (isset($_GET['token']) && $_GET['user'] && $_GET['hash']) {

    $hash = $_GET['hash'];
    if (verify_hash($hash)) {

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if ($token !== false) {

            $api_url = 'https://api.codechef.com/problems/{categoryName}?fields=&offset=&limit=&sortBy=&sortOrder=';

            $apiRequest = new CodechefApiCall($token, $api_url);
            $apiRequest->execute();
            $result = $apiRequest->getResult();
            $resultObj = json_decode($result);

        } else {
            \PhpUseful\EasyHeaders::bad_request();
        }
    } else {
        \PhpUseful\EasyHeaders::bad_request();
    }
} else {
    \PhpUseful\EasyHeaders::bad_request();
}