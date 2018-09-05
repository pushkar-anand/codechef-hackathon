<?php
require_once __DIR__ . '/../vendor/autoload.php';

 function start_my_session()
 {
     session_name('codechef_state');
     session_start();
 }

 function verify_hash(string $hash):bool
 {
     $url = (isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? "https" : "http") . "://$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]";

     $rm = "&hash=$hash";
     $url = str_replace($rm, '', $url);

     $calc_hash = hash('sha256', $url . API_SECRET);
     return $calc_hash == $hash;
 }

function practice_request($token, $url)
{
    $apiRequest = new CodechefApiCall($token, $url);
    $apiRequest->execute();
    $result = $apiRequest->getResult();
    $resultObj = json_decode($result);

    if ($resultObj->status == 'OK') {
        $problems = $resultObj->result->data->content;
        return json_encode($problems);
    } else {
        \PhpUseful\EasyHeaders::bad_request();
        return false;
    }
}