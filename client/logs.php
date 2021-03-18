<?php
$isrc = $_GET['isrc'];

try {
    $url = "http://localhost:8090/logs?wsdl";
    $client = new SoapClient($url, ['features' => SOAP_SINGLE_ELEMENT_ARRAYS]);
//    $from = '2021-03-04T19:06';
//    $to = '2021-03-17T15:03';
    $from = isset($_GET['from']) ? $_GET['from'] : null;
    $to = isset($_GET['to']) ? $_GET['to'] : null;
    $change = isset($_GET['change']) ? $_GET['change'] : null;
    $retVal = (array)$client->getChangeLog(array('arg0' => $isrc, 'arg1' => $from, 'arg2' => $to, 'arg3' => $change));

    $logs = isset($retVal['return']) ? $retVal['return'] : array();
    foreach ($logs as $key => $de) {
        $logs[$key] = (array)$de;
    }
} catch (SoapFault $e) {
    echo($e);
    die();
}

?>

<h2>Changes for <?= $isrc ?></h2>

<form action="logs.php" method="get">
    <input type="hidden" name="isrc" value="<?= $isrc ?>" />

    <label for="change">Change type:</label>
    <select name="change" id="change">
        <option <?= $change === null || $change === '' ? 'selected ' : '' ?>value>All types</option>
        <option <?= $change === 'CREATE' ? 'selected ' : '' ?>value="CREATE">CREATE</option>
        <option <?= $change === 'UPDATE' ? 'selected ' : '' ?>value="UPDATE">UPDATE</option>
        <option <?= $change === 'DELETE' ? 'selected ' : '' ?>value="DELETE">DELETE</option>
    </select><br><br>

    <label for="from">From:</label>
    <input type="datetime-local" id="from" name="from"
           value="$change>" /><br><br>

    <label for="to">To:</label>
    <input type="datetime-local" id="to" name="to"
           value="<?= isset($to) ? $to : '' ?>" /><br><br>

    <input type="submit" value="Submit">
</form>

<table style="border: 1px solid black;">
    <thead>
    <tr>
        <th style="border: 1px solid black;">Change type</th>
        <th style="border: 1px solid black;">Date</th>
    </tr>
    </thead>
    <tbody>
    <?php foreach($logs as $log): ?>
        <tr>
            <td style="border: 1px solid black;"><?= $log['change']; ?></td>
            <td style="border: 1px solid black;"><?= $log['timestamp'];; ?></td>
        </tr>
    <?php endforeach; ?>
    </tbody>
</table>
