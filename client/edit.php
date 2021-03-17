<?php

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $curl = curl_init();
    $title = htmlspecialchars($_POST['name']);
    $isrc = htmlspecialchars($_GET['isrc']);
    $first = htmlspecialchars($_POST['first']);
    $last = htmlspecialchars($_POST['last']);
    $year = htmlspecialchars($_POST['year']);
    $desc = htmlspecialchars($_POST['desc']);

    $postFields = "isrc=$isrc&title=$title&year=$year&firstName=$first&lastName=$last&desc=$desc";

    curl_setopt_array($curl, array(
        CURLOPT_URL => 'http://localhost:8080/myapp/album/update',
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => '',
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => 'PUT',
        CURLOPT_POSTFIELDS => $postFields,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/x-www-form-urlencoded'
        ),
    ));

    $response = curl_exec($curl);

    curl_close($curl);
//    echo $response;

    // ---
    $file = $_FILES['file'];
    $curlFile = new CURLFILE($file['tmp_name']);
    $curlFile->setMimeType($file['type']);

    $curl = curl_init();

    curl_setopt_array($curl, array(
        CURLOPT_URL => 'http://localhost:8080/myapp/album/cover/update/' . $isrc,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => '',
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => 'POST',
        CURLOPT_POSTFIELDS => array('file'=> $curlFile),
    ));

    $response = curl_exec($curl);

    curl_close($curl);
//    echo $response;

    header("Location: http://localhost/client/view.php?isrc=$isrc");
    die();
} else {
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

    if (!isset($album->description)) {
        $album->description = '';
    }

    curl_close($curl);
    echo $response;
}

?>

<form action="edit.php?isrc=<?= $albumISRC; ?>" method="post" enctype="multipart/form-data">
    <label for="name">Album name:</label>
    <input type="text" id="name" name="name" required value="<?= $album->title; ?>"><br><br>
    <label for="first">Artist first name:</label>
    <input type="text" id="first" name="first" required value="<?= $album->artist->firstName; ?>"><br><br>
    <label for="last">Artist last name:</label>
    <input type="text" id="last" name="last" required value="<?= $album->artist->lastName; ?>"><br><br>
    <label for="year">Release year:</label>
    <input type="number" id="year" name="year" required value="<?= $album->year; ?>"><br><br>
    <label for="desc">Description:</label>
    <input type="text" id="desc" name="desc" value="<?= $album->description; ?>"><br><br>
    <label for="file">Artwork:</label>
    <input type="file" accept="image/*" id="file" name="file"><br><br>
    <?php if(isset($album->coverImage)) : ?>
        <label for="del">Delete existing artwork:</label>
        <input type="checkbox" id="del" name="del"><br><br>
    <?php endif; ?>
    <input type="submit" value="Submit">
</form>
