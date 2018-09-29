<?php
require_once __DIR__ . '/../vendor/autoload.php';

use Monolog\Handler\NativeMailerHandler;
use Monolog\Handler\StreamHandler;
use Monolog\Logger;

global $log;

$log = new Logger('codechef');
try {

    $log->pushHandler(new StreamHandler(__DIR__. '/../logs/warn.log', Logger::WARNING));
    $log->pushHandler(new StreamHandler(__DIR__. '/../logs/error.log', Logger::ERROR));
    $log->pushHandler(new StreamHandler(__DIR__. '/../logs/notice.log', Logger::NOTICE));
    $log->pushHandler(new StreamHandler(__DIR__. '/../logs/info.log', Logger::INFO));
    $log->pushHandler(new NativeMailerHandler(DEV_EMAIL, SUBJECT_CRITICAL, FROM_EMAIL, Logger::CRITICAL));
    $log->pushHandler(new NativeMailerHandler(DEV_EMAIL, SUBJECT_EMERGENCY, FROM_EMAIL, Logger::EMERGENCY));
    $log->pushHandler(new NativeMailerHandler(DEV_EMAIL, SUBJECT_ALERT, FROM_EMAIL, Logger::ALERT));

} catch (Exception $e)
{
    error_log('LOGGING: '.$e);
}