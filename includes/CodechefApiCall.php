<?php

require_once __DIR__ . '/../vendor/autoload.php';

class CodechefApiCall extends Curl
{
    function __construct(string $token, string $url)
    {
        parent::__construct();

        $header = array(
            "Accept: application/json",
            "Authorization: Bearer " . $token
        );

        parent::setUrl($url);
        parent::setHeaders($header);
        parent::setReturnTrue();
    }
}