<?php

include("connection.php");
// Get the value of 'username' key from the array
$username = $_POST['username'];

$sql = "SELECT * FROM User WHERE Name = '$username';";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Create an array to hold the data
    $response = array();
    while ($row = $result->fetch_assoc()) {
        $response = array("success" => 1, "id" => $row["Id"]);
    }

} else {
	$response = array("success" => 0);
}

echo json_encode($response);
$conn->close();
?>