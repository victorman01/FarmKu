<?php
// $target_dir = "upload/";

// // Handle the image file upload
// if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['photo'])){
//     $target_dir = 'upload/';
//     $target_file = $target_dir . basename($_FILES['photo']['name']);
//     if (move_uploaded_file($_FILES['photo']['tmp_name'], $target_file)) {
//         $pythonScript = 'predict.py';
//         $command = "python {$pythonScript} {$target_file}";
//         $output = shell_exec($command);

//         // $result = ["result" => $output["result"], "confidence" => $output["confidence"]];
//         // echo json_encode($result);
//         echo $output;
//     } else {
//         $result = ["result" => "failed", "confidence" => "no Confidence"];
//         echo json_encode($result);
//     }
// }

$flaskUrl = 'http://localhost:5000/preprocess';

$filePath = $_FILES['photo']['tmp_name'];
$fileName = $_FILES['photo']['name'];

// Convert data to JSON
$file = new CURLFile($filePath, mime_content_type($filePath), $fileName);

// Initialize cURL session
$curl = curl_init();

// Set the cURL options
curl_setopt($curl, CURLOPT_URL, $flaskUrl);
curl_setopt($curl, CURLOPT_POST, true);
curl_setopt($curl, CURLOPT_POSTFIELDS, ['photo' => $file]);

// Execute the cURL request
$response = curl_exec($curl);

// Close the cURL session
curl_close($curl);

// Print the response
// echo $response;

// $data = json_decode($response, true);

// echo $data;

// $predicted_class = $data['result'];
// $prediction_confidence = $data['confidence'];

// echo 'Predicted class: ' . $predicted_class . '<br>';
// echo 'Prediction confidence: ' . $prediction_confidence . '<br>';
?>