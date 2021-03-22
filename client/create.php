<?php

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $curl = curl_init();
    $title = htmlspecialchars($_POST['name']);
    $isrc = htmlspecialchars($_POST['isrc']);
    $first = htmlspecialchars($_POST['first']);
    $last = htmlspecialchars($_POST['last']);
    $year = htmlspecialchars($_POST['year']);

    $postFields = "isrc=$isrc&title=$title&year=$year&firstName=$first&lastName=$last";

    curl_setopt_array($curl, array(
        CURLOPT_URL => 'http://localhost:8080/myapp/album/insert',
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => '',
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => 'POST',
        CURLOPT_POSTFIELDS => $postFields,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/x-www-form-urlencoded'
        ),
    ));

    $response = curl_exec($curl);

    curl_close($curl);
//    echo $response;

    function isJson($string): bool {
        json_decode($string);
        return (json_last_error() == JSON_ERROR_NONE);
    }

    if (isJson($response)) {
        header("Location: http://localhost/client/view.php?isrc=$isrc");
        die();
    } else {
        die($response);
    }
}

?>

<form action="create.php" method="post">
    <label for="name">Album name:</label>
    <input type="text" id="name" name="name" required><br><br>
    <label for="isrc">ISRC:</label>
    <input type="text" id="isrc" name="isrc" required><br><br>
    <label for="first">Artist first name:</label>
    <input type="text" id="first" name="first" required><br><br>
    <label for="last">Artist last name:</label>
    <input type="text" id="last" name="last" required><br><br>
    <label for="year">Release year:</label>
    <input type="number" id="year" name="year" required><br><br>
    <input type="submit" value="Submit">
</form>
