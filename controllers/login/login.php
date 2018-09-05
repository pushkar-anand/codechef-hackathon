<?php
require_once __DIR__ . '/../vendor/autoload.php';
use PhpUseful\Functions;

$m = new Mustache_Engine(array(
    'loader' => new Mustache_Loader_FilesystemLoader(__DIR__ . '/../views'),
));

$state = Functions::random_string(10);
$_SESSION['state'] = $state;
$url = 'https://api.codechef.com/oauth/authorize?response_type=code&client_id=' . CODECHEF_CLIENT_ID . '&state=' . $state . '&redirect_uri=' . REDIRECT_URI;

echo $m->render('start', array('url' => $url));