<?php
error_reporting(E_ALL ^ E_DEPRECATED); 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
require_once __DIR__ . '/db_config.php';
 
// connecting to db
$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

$result = mysqli_query($db, "SELECT *FROM production_items");
if (!empty($result)) {
    // check for empty result
    if (mysqli_num_rows($result) > 0) {
        $response["product"] = array();
        while ($row = mysqli_fetch_array($result,MYSQLI_BOTH)) {
            // temp user array
            $product = array();
            $date = date("d");
            $product["itemName"] = $row["name"];
            $product["qtyProduced"] = $row["".$date."_qty"]*1;
            for ($i=1; $i < $date; $i++) { 
                if (i<10) {
                    $product["qtyProduced"] += ($row["0".$i."_qty"]*1);
                } else {
                    $product["qtyProduced"] += ($row["".$i."_qty"]*1);
                }
            };
            $product["qtyTarget"] = $row["".$date."_target"]*1;
            for ($i=1; $i < $date; $i++) { 
                if ($i<10) {
                    $product["qtyTarget"] += ($row["0".$i."_target"]*1);
                } else {
                    $product["qtyTarget"] += ($row["".$i."_target"]*1);
                }
            };
            // push single product into final response array
            array_push($response["product"], $product);
        }
        // success
        $response["success"] = 1;
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // no product found
    $response["success"] = 0;
    $response["message"] = "No product found";

    // echo no users JSON
    echo json_encode($response);
}

?>