<?php
include "connection.php";

// Get the user's credentials
$userId = $_POST['userId'];
$content = $_POST['content'];
$posted_on = date('Y-m-d H:i:s');

// Prepare and execute the query to insert a new post
$sql = "INSERT INTO Post (UserId, Content, PostedOn)
	VALUES ('$userId', '$content','$posted_on')";

if ($conn->query($sql) !== TRUE) {
    $response = array('success' => 0, 'message' => 'Error creating post: ' . $conn->error);
    echo json_encode($response);
    $conn->close();
    exit();
}

// New post created successfully, return success message as a JSON object
$response = array('success' => 1, 'message' => 'Post created successfully');
echo json_encode($response);

// Close the connection
$conn->close();			s
?>
