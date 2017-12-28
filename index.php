<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require 'vendor/autoload.php';

$app = new \Slim\App( Routing::getInstance()->getContainer() );
$app = Routing::getInstance()->dispatchRouting( $app );
$app->run();