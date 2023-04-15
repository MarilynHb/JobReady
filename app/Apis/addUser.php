<?php
include "connection.php";

// Get the user's input
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];
$type = $_POST['type'];
$headline = $_POST['headline'];
$about = $_POST['about'];
$location = $_POST['location'];
$industry = $_POST['industry'];
$created_on = date('Y-m-d H:i:s');

// Insert the new user into the database

echo $industry;	
$sqlIndustry = "SELECT Id FROM Industry WHERE Description = '$industry'";
$result = mysqli_query($conn, $sqlIndustry);
if(mysqli_num_rows($result)>0){
	while($row = mysqli_fetch_assoc($result)){
		$industry_id = $row["Id"];
	}
}
echo $industry_id;
$sql = "INSERT INTO User (Email, Name, Password, TypeId, Headline, About, Location, IndustryId, CreatedOn)
        VALUES ('$email', '$username', '$password', '$type', '$headline', '$about', '$location', '$industry_id', '$created_on')";
        
if ($conn->query($sql) !== TRUE) {
    $response = array('success' => 0, 'message' => 'Error creating user account: ' . $conn->error);
    echo json_encode($response);
    $conn->close();
    exit();
}

$user_id = $conn->insert_id;
$skills = $_POST['skills'];

if (!empty($skills)) {
    $skills_sql = "INSERT INTO UserSkill (userId, skillId) VALUES ";
    foreach ($skills as $skill) {
        $skills_sql .= "('$user_id', '$skill'),";
    }
    $skills_sql = rtrim($skills_sql, ",");
    if ($conn->query($skills_sql) !== TRUE) {
        // Error inserting skills, return error message as a JSON object
        $response = array('success' => 0, 'message' => 'Error inserting user skills: ' . $conn->error);
        echo json_encode($response);
        $conn->close();
        exit();
    }
}

// New user created successfully, return success message as a JSON object
$response = array('success' => 1, 'message' => 'User account created successfully');
echo json_encode($response);

// Close the connection
$conn->close();
?>

