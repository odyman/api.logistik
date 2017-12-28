<?php

class Routing extends Singletone {
	private $c = null;
	private $app = null;

	public function __construct(){
		$configuration = [
			'settings' => [
				'displayErrorDetails' => true,
			],
		];
		$this->c = new \Slim\Container( $configuration );
	}
	
	public function getContainer(){
		$this->setRouting();
		$this->setHandler();
		return $this->c;
	}
	/**
     * defining routing dispacthing
     * @param \Slim\App $app
     * @return \Slim\App $app
     */
	public function dispatchRouting( $app ){
		$app->add(function ($request, $response, $next) {
			$SMAPIKEY = $request->getHeader('SMAPIKEY');
			if( !Mkey::getInstance()->isValid( $SMAPIKEY ) ){
				$response->withJSON( array('status'=>false, 'message' => 'failed key'), 403);
			}else{
				$response = $next($request, $response);
			};
			return $response;
		});
		$app->get('/', 'Cindex:getIndex');
		$app->post('/auth', 'Cindex:postAuth');
		$app->group('/api', function () use ( $app ) {
			$app->get('/date', 'Cindex:getDate');
			$app->get('/time', 'Cindex:getTime');
			
			$app->post('/sync', 'Cindex:postSync');
			$app->post('/ubah-password', 'Cindex:postUbahPassword');
			//---> Inbound BAST Module
			$app->get('/inbound/all', 'Cinbound:getInboundList');
			$app->get('/inbound/{id}', 'Cinbound:getInboundById');
			$app->get('/inbound/detail/{id}', 'Cinbound:getInboundDetailList');
			$app->get('/inbound/detailinfo/{id_inbound_bast}/{id_barang}', 'Cinbound:getInboundDetailInfo');
			$app->get('/inbound/detail_list/{id_inbound_bast}/{id_barang}', 'Cinbound:getInboundDetailItemList');
			$app->post('/inbound/scancekQRCodeInbound', 'Cinbound:scancekQRCodeInbound');
			$app->post('/inbound/saveQRCodeInbound', 'Cinbound:saveQRCodeInbound');
			//---> Delivery Module
			$app->get('/delivery/all', 'Cdelivery:getDeliveryList');
			$app->get('/delivery/{id}', 'Cdelivery:getDeliveryById');
			$app->get('/delivery/detail/{id}', 'Cdelivery:getDeliveryDetailList');
			$app->get('/delivery/detailinfo/{idp_delivery_detail}', 'Cdelivery:getDeliveryDetailInfo');
			$app->get('/delivery/detail_list/{idp_delivery}', 'Cdelivery:getDeliveryDetailItemList');
			$app->post('/delivery/scancekQRCodeDelivery', 'Cdelivery:scancekQRCodeDelivery');
			$app->post('/delivery/saveQRCodeDelivery', 'Cdelivery:saveQRCodeDelivery');

		})->add(function ($request, $response, $next) {
			$SMAPIKEY 	= $request->getHeader('SMAPIKEY');
			$SMAPITOKEN = $request->getHeader('SMAPITOKEN');
			$SMAPINIP 	= Mkey::clearNIP( $request->getHeader('SMAPINIP') );
			$SMAPITYPE = $request->getHeader('SMAPITYPE');
			
			if( Muser::getInstance()->validateUserToken( $SMAPIKEY, $SMAPITOKEN, $SMAPINIP ) ) {
				$response = $next($request, $response);
			}else{
				$response->withJSON( array('status'=>false, 'message' => 'failed token or key'), 403 );
			};
			return $response;
		});	
		return $app;
	}
	
	public function setRouting() {
		$this->c['Cindex'] = function ($c) {
			return new Cindex();
		};
	}
	
	public function setHandler(){
		$this->c['notFoundHandler'] = function ($c) {
			return function ($request, $response) use ($c) {
				return $c['response']
					->withStatus(404)
					->withJSON(array('status'=>false,'message'=>'Resources not found'), 404);
			};
		};
		$this->c['errorHandler'] = function ($c) {
			return function ($request, $response, $exception) use ($c) {
				echo $exception->getMessage();
				exit;
				return $c['response']->withStatus(500)
									->withJSON(array('status'=>false,'message'=>'Something went wrong!'), 500);
			};
		};
		$this->c['notAllowedHandler'] = function ($c) {
			return function ($request, $response, $methods) use ($c) {
					return $c['response']
						->withStatus(405)
						->withHeader('Allow', implode(', ', $methods))
						->withJSON(array('status'=>false,'message'=>'Method must be one of: ' . implode(', ', $methods)), 405);
			};
		};
		$this->c['Logger'] = function ($c) {
            $logger   = new Monolog\Logger('logger');
            $filename = __DIR__ . '/../log/error.log';
            $stream   = new Monolog\Handler\StreamHandler($filename, Monolog\Logger::DEBUG);
            $fingersCrossed = new Monolog\Handler\FingersCrossedHandler($stream, Monolog\Logger::ERROR);
            $logger->pushHandler($fingersCrossed);

            return $logger;
        };
        $this->c['errorHandler'] = function ($c)
        {
            return new App\Handlers\Error($c['Logger']);
        };
	}
}