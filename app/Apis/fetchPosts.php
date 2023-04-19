<?php
include "connection.php";

$sql = "select u.Name, p.Content, u.Headline from Post p INNER join User u on p.UserId = u.Id order by p.PostedOn desc";
$result = mysqli_query($conn, $sql);

// Build JSON array of industries
$posts['posts'] = array();
if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {
        $post = array(
            "username" => $row["Name"],
            "content" => $row["Content"],
			"headline" => $row["Headline"]
        );
        array_push($posts['posts'], $post);
    }
}

// Return JSON array of industries
header('Content-Type: application/json');
echo json_encode($posts);

// Close database connection
mysqli_close($conn);

?>