<?php
require_once __DIR__ . '/../../vendor/autoload.php';
use PhpUseful\Functions;

if(isset($_GET['token']) && isset($_GET['user']) && isset($_GET['hash']) ) {

    $hash = $_GET['hash'];
    if(verify_hash($hash)){

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if($token !== false) {

            $api_url1 = CODECHEF_API_BASE_URL . '/contests?fields=code,name,startDate,endDate&status=present&offset=1&limit=100';
            $api_url2 = CODECHEF_API_BASE_URL . '/contests?fields=code,name,startDate,endDate&status=future&offset=1&limit=100';

            $apiRequest1 = new CodechefApiCall($token, $api_url1);
            $apiRequest1->execute();
            $result1 = $apiRequest1->getResult();
            $resultObj1 = json_decode($result1);

            $apiRequest2 = new CodechefApiCall($token, $api_url2);
            $apiRequest2->execute();
            $result2 = $apiRequest2->getResult();
            $resultObj2 = json_decode($result2);

            if ($resultObj1->status == 'OK' && $resultObj2->status == 'OK') {

                $contestList1 = $resultObj1->result->data->content->contestList;
                error_log("1: $contestList1");
                $contestList2 = $resultObj2->result->data->content->contestList;
                error_log("2: $contestList2");

                $merged = (object) array_merge((array) $contestList1, (array) $contestList2);

                \PhpUseful\EasyHeaders::json_header();
                echo json_encode($merged);
            }else
            {
                error_log($result1);
            }
        }else
        {
            \PhpUseful\EasyHeaders::bad_request();
        }

    }else
    {
        \PhpUseful\EasyHeaders::bad_request();
    }
}else
{
    \PhpUseful\EasyHeaders::bad_request();
}