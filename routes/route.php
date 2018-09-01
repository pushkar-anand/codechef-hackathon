<?php
require_once __DIR__ . '/../vendor/autoload.php';
require_once __DIR__ . '/../logs/initiateLogger.php';

use EasyRoute\Route;

$route = new Route();

try {

    $route->addMatch('POST', '/register', function ()
    {

    });

} catch (Exception $exception) {

    $log->error('Error in routes: '. $exception);

}