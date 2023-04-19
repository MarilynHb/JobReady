<?php
include "connection.php";

// Get the user's input
$userId= $_POST['userId'];
// $email = $_POST['email'];
$headline = $_POST['headline'];
$location = $_POST['location'];
// $industry = $_POST['industry'];
$about = $_POST['about'];

// Update the user into the database
// echo $industry;
// $sqlIndustry = "SELECT Id FROM Industry WHERE Description = '$industry'";
// $result = mysqli_query($conn, $sqlIndustry);
// if(mysqli_num_rows($result)>0){
// 	while($row = mysqli_fetch_assoc($result)){
// 		$industry_id = $row["Id"];
// 	}
// }
// echo $industry_id;
$sql = "UPDATE User SET
        Headline = '$headline',
        About = '$about',
        Location = '$location'
        where Id = $userId";
if ($conn->query($sql) !== TRUE) {
    $response = array('success' => 0, 'message' => 'Error creating user account: ');
    echo json_encode($response);
    $conn->close();
    exit();
}

// User edited successfully, return success message as a JSON object
$response = array('success' => 1, 'message' => 'User account edited successfully');
echo json_encode($response);

// Close the connection
$conn->close();
?>

