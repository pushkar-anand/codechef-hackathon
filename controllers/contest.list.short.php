<?php
require_once __DIR__ . '/../vendor/autoload.php';
use PhpUseful\Functions;

if(isset($_GET['token']) && $_GET['user'] && $_GET['hash']) {

    $hash = $_GET['hash'];
    verify_hash($hash);

    $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
    if($token !== false) {

        $api_url = CODECHEF_API_BASE_URL . '/contests?fields=code,name,startDate,endDate&status=present&offset=1&limit=3';

        $apiRequest = new CodechefApiCall($token, $api_url);
        $apiRequest->execute();
        $result = $apiRequest->getResult();
        $resultObj = json_decode($result);

        if($resultObj->status == 'OK')
        {
            $contestList = $resultObj->result->data->content->contestList;
            \PhpUseful\EasyHeaders::json_header();
            echo json_encode($contestList);
        }

    }else
    {
        \PhpUseful\EasyHeaders::bad_request();
    }
}else
{
    \PhpUseful\EasyHeaders::bad_request();
}