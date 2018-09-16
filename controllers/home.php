<?php
require_once __DIR__ . '/../vendor/autoload.php';

$m = new Mustache_Engine(array(
    'loader' => new Mustache_Loader_FilesystemLoader(__DIR__ . '/../views'),
));

$apk_url = "";

echo $m->render('home', array('apk_url' => $apk_url));