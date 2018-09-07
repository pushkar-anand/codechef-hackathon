<?php
require_once __DIR__ . '/../../vendor/autoload.php';
use PhpUseful\Functions;

if(isset($_GET['token']) && isset($_GET['user']) && isset($_GET['hash']) ) {

    $hash = $_GET['hash'];
    if(verify_hash($hash)){

        $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));
        if($token !== false) {

            $api_url1 = CODECHEF_API_BASE_URL . '/contests?fields=code,name,startDate,endDate&status=present&limit=100';

            $apiRequest1 = new CodechefApiCall($token, $api_url1);
            $apiRequest1->execute();
            $result1 = $apiRequest1->getResult();
            $resultObj1 = json_decode($result1);

            $api_url2 = CODECHEF_API_BASE_URL . '/contests?fields=code,name,startDate,endDate&status=future&limit=100';

            $apiRequest2 = new CodechefApiCall($token, $api_url2);
            $apiRequest2->execute();
            $result2 = $apiRequest2->getResult();
            $resultObj2 = json_decode($result2);

            if ($resultObj1->status == 'OK' && $resultObj2->status == 'OK') {

                $contestListPresent = $resultObj1->result->data->content->contestList;
                $contestListFuture = $resultObj2->result->data->content->contestList;

                $present = array("present" => $contestListPresent);
                $future = array("future" => $contestListFuture);

                $merged = array_merge( $contestListPresent, $contestListFuture);

                \PhpUseful\EasyHeaders::json_header();
                echo json_encode($merged);
                error_log(json_encode(array_merge($present, $future)));
            }else
            {
                error_log($result1);
                error_log($result2);
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