<?php
include "connection.php";

$sql = "SELECT * FROM Industry";
$result = mysqli_query($conn, $sql);

// Build JSON array of industries
$industries['industries'] = array();
if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {
        $industry = array(
            "id" => $row["Id"],
            "description" => $row["Description"]
        );
        array_push($industries['industries'], $industry);
    }
}

// Return JSON array of industries
header('Content-Type: application/json');
echo json_encode($industries);

// Close database connection
mysqli_close($conn);

?>
