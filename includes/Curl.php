<?php
class Curl
{
    private $curl;
    private $result;
    protected $http_response_code;
    public function __construct()
    {
        $this->curl = curl_init();
    }
    public function usePost(bool $bool)
    {
        if ($bool) {
            curl_setopt($this->curl, CURLOPT_POST, 1);
        }
    }
    public function setUrl(string $url)
    {
        error_log("Sending curl request to " . $url);
        curl_setopt($this->curl, CURLOPT_URL, $url);
    }
    public function setHeaders(array $headers)
    {
        curl_setopt($this->curl, CURLOPT_HTTPHEADER, $headers);
    }
    public function setReturnTrue()
    {
        curl_setopt($this->curl, CURLOPT_RETURNTRANSFER, true);
    }
    public function setArrayPostData(array $data)
    {
        curl_setopt($this->curl, CURLOPT_POSTFIELDS, $data);
    }
    public function setStringPostData(string $data)
    {
        curl_setopt($this->curl, CURLOPT_POSTFIELDS, $data);
    }
    public function execute()
    {
        curl_setopt($this->curl, CURLINFO_HEADER_OUT, true);
        $this->result = curl_exec($this->curl);
        $this->http_response_code = curl_getinfo($this->curl, CURLINFO_HTTP_CODE);
        error_log("Curl Response: $this->result");
    }
    public function getSentHeader()
    {
        return curl_getinfo($this->curl, CURLINFO_HEADER_OUT);
    }
    public function getResult()
    {
        return $this->result;
    }
    public function  __destruct()
    {
        curl_close($this->curl);
    }
}