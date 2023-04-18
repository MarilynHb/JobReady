<?php
include "connection.php";

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "SELECT * FROM User WHERE Email = '$email' AND Password = '$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
   while($row = mysqli_fetch_assoc($result)) {
    $response = array('success' => 1, 'message' => 'Logged in successfully', 'id' => 	 $row["Id"], 'username' => $row["Name"]);
}
    echo json_encode($response);
}

else {
    $response = array('success' => 0, 'message' => 'Invalid username or password');
    echo json_encode($response);
}

$conn->close();
?>
