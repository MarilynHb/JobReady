<?php
include "connection.php";

// Get the follower and followee IDs from the request
$user_id = $_POST['user_id'];
$following_id = $_POST['following_id'];
//$has_notif_on = $_POST['has_notif_on'];
$followed_on = date('Y-m-d H:i:s');

// Insert the follower-followee relationship into the database
$sql = "DELETE FROM Follower where UserId = '$user_id' AND FollowingId = '$following_id'";

if ($conn->query($sql) === TRUE) {
    // Return success message as a JSON object
    $response = array('success' => 1, 'message' => 'User unfollowed successfully');
    echo json_encode($response);
} else {
    // Return error message as a JSON object
    $response = array('success' => 0, 'message' => 'Error: ' . $sql . '<br>' . $conn->error);
    echo json_encode($response);
}

// Close the connection
$conn->close();
?>

