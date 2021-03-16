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

if (isset($_GET['name'])) {
    $search = trim($_GET['name']);
    if ($search !== '') {
        foreach ($jsonArray as $key => $album) {
            if (strpos($album->title, $search) === false) {
                unset($jsonArray[$key]);
            }
        }
    }
}

curl_close($curl);
echo $response;

?>

<form action="all.php" method="get">
    <label for="name">Album name:</label>
    <input type="text" id="name" name="name"><br><br>
    <input type="submit" value="Submit">
</form>

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
            <?php $isrcHTML = htmlspecialchars($album->isrc); ?>
            <td style="border: 1px solid black;"><a href="view.php?isrc=<?= $isrcHTML ?>"><?= $album->title; ?></a></td>
            <td style="border: 1px solid black;"><?= $album->artist->firstName . ' ' . $album->artist->lastName; ?></td>
            <td style="border: 1px solid black;"><?= $album->isrc; ?></td>
            <td style="border: 1px solid black;"><?= $album->year; ?></td>
        </tr>
    <?php endforeach; ?>
    </tbody>
</table>
