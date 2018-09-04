<?php
 function start_my_session()
 {
     session_name('codechef_state');
     session_start();
 }

 function verify_hash(string $hash, string $url)
 {
     $calc_hash = hash('sha-256', $url . API_SECRET);
     var_dump($calc_hash);
 }