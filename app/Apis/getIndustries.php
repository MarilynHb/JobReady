<?php
include "connection.php";

/ Create a SQL query to retrieve all industries
$sql = "SELECT id, description FROM industry";
$result = mysqli_query($conn, $sql);

// Check for errors in the query
if (!$result) {
    die("Error retrieving industries: " . mysqli_error($conn));
}

// Create an array to hold the industries
$industries = array();

// Loop through each row in the result set and add the industry to the array
while ($row = mysqli_fetch_assoc($result)) {
    $industry = array(
        "id" => $row["id"],
        "description" => $row["description"]
    );
    array_push($industries, $industry);
}

// Close the database connection
mysqli_close($conn);

// Return the industries as a JSON array
header('Content-Type: application/json');
echo json_encode($industries);
$conn->close();
?>