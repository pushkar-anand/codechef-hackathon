<?php
require_once __DIR__ . '/../vendor/autoload.php';
require_once __DIR__ . '/../logs/initiateLogger.php';
use EasyRoute\Route;

start_my_session();

$route = new Route();

try {

    $route->addMatch('GET', '/login', __DIR__ . '/../controllers/login.php');
    $route->addMatch('GET', '/login/redirect', __DIR__ . '/../controllers/redirect.php');
    $route->addMatch('GET', '/contest/single', __DIR__ . '/../controllers/contest.single.php');
    $route->addMatch('GET', '/recommend', __DIR__ . '/../controllers/recommend.php');


    $route->execute();

} catch (Exception $exception) {

    $log->error('Error in routes: ' . $exception);

}