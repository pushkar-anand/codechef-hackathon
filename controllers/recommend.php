<?php
require_once __DIR__ . '/../vendor/autoload.php';
use PhpUseful\Functions;

if(isset($_GET['token']) && $_GET['user']) {

    $token = User::getCodechefToken($_GET['token'], Functions::escape_input($_GET['user']));

    $api_url = CODECHEF_API_BASE_URL . '/problems/{categoryName}?fields=&offset=&limit=&sortBy=&sortOrder=';

    if ($token !== false) {

    }else
    {
        \PhpUseful\EasyHeaders::bad_request();
    }
}else
{
    \PhpUseful\EasyHeaders::bad_request();
}