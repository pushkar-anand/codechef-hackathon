<?php
require_once __DIR__ . '/../vendor/autoload.php';
require_once __DIR__ . '/../logs/initiateLogger.php';
use EasyRoute\Route;

start_my_session();

$route = new Route();

try {

    $route->addMatch('GET', '/login', __DIR__ . '/../controllers/login/login.php');
    $route->addMatch('GET', '/login/redirect', __DIR__ . '/../controllers/login/redirect.php');

    $route->addMatch('GET', '/contest/list/short', __DIR__ . '/../controllers/contest/list.short.php');
    $route->addMatch('GET', '/contest/list/all', __DIR__ . '/../controllers/contest/list.all.php');
    $route->addMatch('GET', '/contest/problems/list', __DIR__ . '/../controllers/contest/problems.list.php');
    $route->addMatch('GET', '/contest/problem/details', __DIR__ . '/../controllers/contest/problem.detail.php');

    $route->addMatch('GET', '/recommend', __DIR__ . '/../controllers/recommend.php');

    $route->addMatch('GET', '/practice/beginner', __DIR__ . '/../controllers/practice/beginner.php');
    $route->addMatch('GET', '/practice/easy', __DIR__ . '/../controllers/practice/easy.php');
    $route->addMatch('GET', '/practice/medium', __DIR__ . '/../controllers/practice/medium.php');
    $route->addMatch('GET', '/practice/hard', __DIR__ . '/../controllers/practice/hard.php');
    $route->addMatch('GET', '/practice/challenge', __DIR__ . '/../controllers/practice/challenge.php');
    $route->addMatch('GET', '/practice/peer', __DIR__ . '/../controllers/practice/peer.php');

    $route->addMatch('GET', '/problem/details', __DIR__ . '/../controllers/problem.details.php');

    $route->addMatch('GET', '/ide', __DIR__ . '/../controllers/ide.php');



    $route->execute();

} catch (Exception $exception) {

    $log->error('Error in routes: ' . $exception);

}