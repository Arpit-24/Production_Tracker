<?php
require __DIR__ . '/vendor/autoload.php';
use Google\Spreadsheet\DefaultServiceRequest;
use Google\Spreadsheet\ServiceRequestFactory;
putenv('GOOGLE_APPLICATION_CREDENTIALS=' . __DIR__ . '/client_secret.json');
$client = new Google_Client;
$client->useApplicationDefaultCredentials();
 
$client->setApplicationName("PS1 App Project");
$client->setScopes(['https://www.googleapis.com/auth/drive','https://spreadsheets.google.com/feeds']);
 
if ($client->isAccessTokenExpired()) {
    $client->refreshTokenWithAssertion();
}
 
$accessToken = $client->fetchAccessTokenWithAssertion()["access_token"];
ServiceRequestFactory::setInstance(
    new DefaultServiceRequest($accessToken)
);
// Get our spreadsheet
$spreadsheet = (new Google\Spreadsheet\SpreadsheetService)
   ->getSpreadsheetFeed()
   ->getByTitle('PS1 Project');
 
// Get the first worksheet (tab)
$worksheets = $spreadsheet->getWorksheetFeed()->getEntries();
$worksheet = $worksheets[0];
$cellFeed = $worksheet->getCellFeed();
 
$rows = $cellFeed->toArray();
// $topLeftCornerCell = $cellFeed->getCell(1, 1);
// echo $cellFeed->getCell(1, 1);
// echo $topLeftCornerCell->getContent();
require_once __DIR__ . '/db_config.php';
$response = array();
$flag = 0;
$i =5;
$proceed = 1;
$quantities = array();
$targets = array();
$name_array = array();
while ($proceed == 1) {
	$name = "";
	if ($cellFeed->getCell($i, 2) != null) {
		$name = $cellFeed->getCell($i, 2)->getContent();
		if ($name == "MAIN TOTAL") {
			$proceed = 0;
		}
	}
	if ($name != "" && $name != "Total Shots" && $name != "Sub Total : " && $name != "Total Shots (DC 9 TO DC - 10)" && $name != "MAIN TOTAL") {
		array_push($name_array, $name);
		$nested_quant = array();
		$nested_target = array();
		for ($j=5; $j < 67; $j+=2) {
			if ($cellFeed->getCell($i, $j) != null) {
				array_push($nested_quant, ($cellFeed->getCell($i, $j)->getContent())*1);
			} else {
				array_push($nested_quant, 0);
			}
		}
		for ($l=6; $l < 67; $l+=2) {
			if ($cellFeed->getCell($i, $l) != null) {
				array_push($nested_target, ($cellFeed->getCell($i, $l)->getContent())*1);
			} else {
				array_push($nested_target, 0);
			}
		}
		array_push($quantities, $nested_quant);
		array_push($targets, $nested_target);
	}
	$i++;
}
$dbcon = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
$del_result = mysqli_query($dbcon, "DELETE FROM `production_items` WHERE 1");
mysqli_close($dbcon);
$query_string = "INSERT INTO `production_items` (`serial`, `name`, `01_qty`, `01_target`, `02_qty`, `02_target`, `03_qty`, `03_target`, `04_qty`, `04_target`, `05_qty`, `05_target`, `06_qty`, `06_target`, `07_qty`, `07_target`, `08_qty`, `08_target`, `09_qty`, `09_target`, `10_qty`, `10_target`, `11_qty`, `11_target`, `12_qty`, `12_target`, `13_qty`, `13_target`, `14_qty`, `14_target`, `15_qty`, `15_target`, `16_qty`, `16_target`, `17_qty`, `17_target`, `18_qty`, `18_target`, `19_qty`, `19_target`, `20_qty`, `20_target`, `21_qty`, `21_target`, `22_qty`, `22_target`, `23_qty`, `23_target`, `24_qty`, `24_target`, `25_qty`, `25_target`, `26_qty`, `26_target`, `27_qty`, `27_target`, `28_qty`, `28_target`, `29_qty`, `29_target`, `30_qty`, `30_target`, `31_qty`, `31_target`) VALUES ";
$format = "$s%s";
for ($k=0; $k < count($quantities); $k++) {
	$query_string = $query_string."(NULL, '".$name_array[$k]."', '".$quantities[$k][0]."', ".$targets[$k][0].", '".$quantities[$k][1]."', ".$targets[$k][1].", '".$quantities[$k][2]."', ".$targets[$k][2].", '".$quantities[$k][3]."', ".$targets[$k][3].", '".$quantities[$k][4]."', ".$targets[$k][4].", '".$quantities[$k][5]."', ".$targets[$k][5].", '".$quantities[$k][6]."', ".$targets[$k][6].", '".$quantities[$k][7]."', ".$targets[$k][7].", '".$quantities[$k][8]."', ".$targets[$k][8].", '".$quantities[$k][9]."', ".$targets[$k][9].", '".$quantities[$k][10]."', ".$targets[$k][10].", '".$quantities[$k][11]."', ".$targets[$k][11].", '".$quantities[$k][12]."', ".$targets[$k][12].", '".$quantities[$k][13]."', ".$targets[$k][13].", '".$quantities[$k][14]."', ".$targets[$k][14].", '".$quantities[$k][15]."', ".$targets[$k][15].", '".$quantities[$k][16]."', ".$targets[$k][16].", '".$quantities[$k][17]."', ".$targets[$k][17].", '".$quantities[$k][18]."', ".$targets[$k][18].", '".$quantities[$k][19]."', ".$targets[$k][19].", '".$quantities[$k][20]."', ".$targets[$k][20].", '".$quantities[$k][21]."', ".$targets[$k][21].", '".$quantities[$k][22]."', ".$targets[$k][22].", '".$quantities[$k][23]."', ".$targets[$k][23].", '".$quantities[$k][24]."', ".$targets[$k][24].", '".$quantities[$k][25]."', ".$targets[$k][25].", '".$quantities[$k][26]."', ".$targets[$k][26].", '".$quantities[$k][27]."', ".$targets[$k][27].", '".$quantities[$k][28]."', ".$targets[$k][28].", '".$quantities[$k][29]."', ".$targets[$k][29].", '".$quantities[$k][30]."', ".$targets[$k][30].")";
	if ($k <= count($quantities) - 2) {
		$query_string = $query_string.", ";
	}
}
$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
$result = mysqli_query($db, $query_string);
if ($result) {
    // successfully inserted
    $response["success"] = 1;
    $response["message"] = "Products Updated";
} else {
	// Handle Error
	// echo $i;
	$flag = 1;
}
mysqli_close($db);
if ($flag == 1) {
	$response["success"] = 0;
	$response["message"] = "Products Not Updated";
}
echo json_encode($response);