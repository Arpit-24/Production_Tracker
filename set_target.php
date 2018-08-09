<?php
$response = array();
if (isset($_POST['itemName']) && isset($_POST['target'])) {
	$itemName = $_POST['itemName'];
	$target = ($_POST['target'])*1;
	$targetdate;
	$format1 = "0%d_target";
	$format2 = "%d_target";
	if (((date("d")*1) + 1) < 10) {
		$targetdate = sprintf($format1, ((date("d")*1) + 1));
	} else {
		$targetdate = sprintf($format2, ((date("d")*1) + 1));
	}
	require_once __DIR__ . '/db_config.php';
	 
	// connecting to db
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
	// $result = mysqli_query($db, "UPDATE production_items SET $targetdate = $target WHERE production_items, name = $itemName");
	$result = mysqli_query($db, "UPDATE `production_items` SET `".$targetdate."` = '".$target."' WHERE `production_items`.`name` = '".$itemName."'");
	if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "$itemName, $target, $targetdate";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}