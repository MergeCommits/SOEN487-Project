<?php

$curl = curl_init();

curl_setopt_array($curl, array(
    CURLOPT_URL => 'http://localhost:8080/myapp/album/all',
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'GET',
));

$response = curl_exec($curl);
$jsonArray = json_decode($response);

curl_close($curl);
echo $response;

?>

<table style="border: 1px solid black;">
    <thead>
        <tr>
            <th style="border: 1px solid black;">Title</th>
            <th style="border: 1px solid black;">Artist</th>
            <th style="border: 1px solid black;">ISRC</th>
            <th style="border: 1px solid black;">Release Year</th>
        </tr>
    </thead>
    <tbody>
    <?php foreach($jsonArray as $album): ?>
        <tr>
            <td style="border: 1px solid black;"><?= $album->title; ?></td>
            <td style="border: 1px solid black;"><?= $album->artist->firstName . ' ' . $album->artist->lastName; ?></td>
            <td style="border: 1px solid black;"><?= $album->isrc; ?></td>
            <td style="border: 1px solid black;"><?= $album->year; ?></td>
        </tr>
    <?php endforeach; ?>
    </tbody>
</table>
