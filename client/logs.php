<?php
$isrc = $_GET['isrc'];

try {
    $url = "http://localhost:8090/logs?wsdl";
    $client = new SoapClient($url, ['features' => SOAP_SINGLE_ELEMENT_ARRAYS]);
    $retVal = (array)$client->getChangeLog(array('arg0' => $isrc));

    foreach ($retVal['return'] as $de) {
        var_dump((array)$de);
    }
} catch (SoapFault $e) {
    echo($e);
}