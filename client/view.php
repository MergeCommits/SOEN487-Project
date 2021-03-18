<?php

$albumISRC = $_GET['isrc'];

$curl = curl_init();

curl_setopt_array($curl, array(
    CURLOPT_URL => 'http://localhost:8080/myapp/album/get/' . $albumISRC,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => '',
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 0,
    CURLOPT_FOLLOWLOCATION => true,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => 'GET',
));

$response = curl_exec($curl);
$album = json_decode($response);

if (isset($album->coverImage)) {
    $artwork_src = 'data:' . $album->coverImage->mimeType . ';base64,' . $album->coverImage->image;
}

if (!isset($album->description)) {
    $album->description = '';
}


curl_close($curl);
echo $response;

?>

<table style="border: 1px solid black;">
    <tbody>
    <tr>
        <td><a href="edit.php?isrc=<?= $album->isrc; ?>">Edit</a></td>
        <td><a href="delete.php?isrc=<?= $album->isrc; ?>">Delete</a></td>
    </tr>
    <tr>
        <td colspan="2"><a href="logs.php?isrc=<?= $album->isrc; ?>">View all logs</a></td>
    </tr>
    <tr>
        <th>Title</th>
        <td style="border: 1px solid black;"><?= $album->title; ?></td>
    </tr>
    <tr>
        <th>Artist</th>
        <td style="border: 1px solid black;"><?= $album->artist->firstName . ' ' . $album->artist->lastName; ?></td>
    </tr>
    <tr>
        <th>ISRC</th>
        <td style="border: 1px solid black;"><?= $album->isrc; ?></td>
    </tr>
    <tr>
        <th>Release Year</th>
        <td style="border: 1px solid black;"><?= $album->year; ?></td>
    </tr>
    <tr>
        <th>Description</th>
        <td style="border: 1px solid black;"><?= $album->description; ?></td>
    </tr>
    <?php if(isset($artwork_src)) : ?>
    <tr>
        <th>Artwork</th>
        <td style="border: 1px solid black;"><img src="<?= $artwork_src; ?>" alt="Artwork"></td>
    </tr>
    <?php endif; ?>
    </tbody>
</table>
