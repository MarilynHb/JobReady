<?php
$HOST = "localhost";
$USR = "root";
$PASS = "";
$DB = "JobReady";

// Create connection
$conn = new mysqli($HOST, $USR, $PASS, $DB);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>

