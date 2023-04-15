<?php
include "connection.php";

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "SELECT * FROM User WHERE Email = '$email' AND Password = '$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $response = array('success' => 1, 'message' => 'Logged in successfully');
    echo json_encode($response);
} else {
    $response = array('success' => 0, 'message' => 'Invalid username or password');
    echo json_encode($response);
}

$conn->close();
?>

