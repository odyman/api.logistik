<?php

$http		= 'http' . ((isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] == 'on') ? 's' : '') . '://';
$newurl		= str_replace("index.php","", $_SERVER['SCRIPT_NAME']);
$baseurl	= "$http" . $_SERVER['SERVER_NAME'] . "" . $newurl;

return array(
	"db" => array(
		//--> Development
		// "hostname" => "localhost",
		// "username" => "root",
		// "password" => "",
		//--> Staging
		"hostname" => "192.168.0.10",
		"username" => "root",
		"password" => "simpeg",		
		//--> Production
		// "hostname" => "192.168.0.9",
		// "username" => "root",
		// "password" => "Exml#17",		
		"database" => "db_access_logistic"
	),
	"api" => array(
		"key" => "d9d0df1575dfd37ea087404c853b64ee"
	),
	"baseurl" => $baseurl
);