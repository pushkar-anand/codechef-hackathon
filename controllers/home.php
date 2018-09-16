<?php
require_once __DIR__ . '/../vendor/autoload.php';

$m = new Mustache_Engine(array(
    'loader' => new Mustache_Loader_FilesystemLoader(__DIR__ . '/../../views'),
));

echo $m->render('home');