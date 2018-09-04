<?php
 function start_my_session()
 {
     session_name('codechef_state');
     session_start();
 }

 function verify_hash(string $hash):bool
 {
     $url = (isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? "https" : "http") . "://$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]";
     error_log($url);
     $rm = "&hash=$hash";

     $url = str_replace($rm, '', $url);


     $calc_hash = hash('sha256', $url . API_SECRET);
     return $calc_hash == $hash;
 }