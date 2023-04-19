<?php
include "connection.php";

// Get the user's credentials
$username = $_POST['username'];
$location = $_POST['location'];
$about = $_POST['about'];
$headline = $_POST['headline'];



$sql = "INSERT INTO user (name, location, about, headline)
	VALUES ('$username', '$location','$about','headline')";

if ($conn->query($sql) !== TRUE) {
    $response = array('success' => 0, 'message' => 'Error' . $conn->error);
    echo json_encode($response);
    $conn->close();
    exit();
}

$response = array('success' => 1, 'message' => 'profile edited successfully');
echo json_encode($response);

// Close the connection
$conn->close();			s
?>