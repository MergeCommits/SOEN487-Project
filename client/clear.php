<?php
$isrc = $_GET['isrc'];

try {
    $url = "http://localhost:8090/logs?wsdl";
    $client = new SoapClient($url, ['features' => SOAP_SINGLE_ELEMENT_ARRAYS]);
    $retVal = (array)$client->clearLogs(array('arg0' => $isrc));
} catch (SoapFault $e) {
    echo($e->getMessage());
    die();
}

?>
