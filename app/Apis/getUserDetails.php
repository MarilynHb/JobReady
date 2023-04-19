<?php

include("connection.php");
// Get the value of 'username' key from the array
$userId = $_POST['id'];

$sql = "SELECT * FROM User WHERE Id = '$userId';";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Create an array to hold the data
    $data = array();
    while ($row = $result->fetch_assoc()) {
        $data = array(
            "id" => $row["Id"],
            "email" => $row["Email"],
            "username" => $row["Name"],
            "headline" => $row["Headline"],
            "type" => $row["TypeId"],
            "industry" => $row["IndustryId"],
            "about" => $row["About"],
            "location" => $row["Location"],
            "createdOn" => $row["CreatedOn"]);
    }

    // Convert the data array to JSON and send it to the Java file
    echo json_encode($data);
} else {
    echo "User does not exist";
}

$conn->close();
?>