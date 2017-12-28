<?php

/**
 * Controller untuk Delivery
 *
 * @author Odyman <odyodyman@yahoo.com>
 */
class Cdelivery extends Controllers {

    public function __construct() {
        parent::__construct();
    }

     /**
     * delivery list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getDeliveryList($request, $response, $args) {
    	$kode_unker = "";
    	if($request->getQueryParam('kode_unker'))
    		$kode_unker = $request->getQueryParam('kode_unker'); 
    	$perpage = 10;
        if ($request->getQueryParam('per_page'))
            $perpage = $request->getQueryParam('per_page');
        $page = 1;
        if ($request->getQueryParam('page'))
            $page = $request->getQueryParam('page');
        $query = "";
        if ($request->getQueryParam('query'))
            $query = $request->getQueryParam('query');
        $sort = "";
        if ($request->getQueryParam('sort'))
            $sort = $request->getQueryParam('sort');

        return $response->withJSON(Mdeliver::getInstance()->getDeliveryList($kode_unker, $perpage, $page, $query, $sort));        
    }   

    public function getDeliveryById( $request, $response, $args )
    {
        $id = $args['id'];
        $result = Mdeliver::getInstance()->getDeliveryById($id);

        return $response->withJSON($result);
    }

     /**
     * delivery detail list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getDeliveryDetailList($request, $response, $args) {
    	$IDP_Delivery = "";
    	if($args['id'])
    		$IDP_Delivery = $args['id']; 
    	$perpage = 10;
        if ($request->getQueryParam('per_page'))
            $perpage = $request->getQueryParam('per_page');
        $page = 1;
        if ($request->getQueryParam('page'))
            $page = $request->getQueryParam('page');
        $query = "";
        if ($request->getQueryParam('query'))
            $query = $request->getQueryParam('query');
        $sort = "";
        if ($request->getQueryParam('sort'))
            $sort = $request->getQueryParam('sort');

        return $response->withJSON(Mdeliver::getInstance()->getDeliveryDetailList($IDP_Delivery, $perpage, $page, $query, $sort));        
    }   

    public function getDeliveryDetailInfo( $request, $response, $args )
    {
        $IDP_Delivery_Detail = $args['idp_delivery_detail'];        
        $result = Mdeliver::getInstance()->getDeliveryDetailInfo($IDP_Delivery_Detail);

        return $response->withJSON($result);
    }

    /**
     * delivery detail item list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getDeliveryDetailItemList($request, $response, $args) {
    	$IDP_Delivery = "";
    	if($args['idp_delivery'])
    		$IDP_Delivery = $args['idp_delivery'];     	
    	$perpage = 10;
        if ($request->getQueryParam('per_page'))
            $perpage = $request->getQueryParam('per_page');
        $page = 1;
        if ($request->getQueryParam('page'))
            $page = $request->getQueryParam('page');
        $query = "";
        if ($request->getQueryParam('query'))
            $query = $request->getQueryParam('query');
        $sort = "";
        if ($request->getQueryParam('sort'))
            $sort = $request->getQueryParam('sort');

        return $response->withJSON(Mdeliver::getInstance()->getDeliveryDetailItemList($IDP_Delivery, $perpage, $page, $query, $sort));        
    }   

    public function scancekQRCodeDelivery($request, $response, $args) {            
        $errorMessage = '';         
        return $response->withJSON(Mdeliver::getInstance()->scancekQRCodeDelivery($request->getParsedBody(), $errorMessage));
    }

    public function saveQRCodeDelivery($request, $response, $args) {
        $model = Mdeliver::getInstance($request);
        $errorMessage = '';
        // return $response->withJSON(Mdeliver::getInstance()->saveQRCodeDelivery($request->getParsedBody(), $errorMessage));
        if (Mdeliver::getInstance()->saveQRCodeDelivery($request->getParsedBody(), $errorMessage))
        {
            $this->rSuccess("Delivery berhasil disimpan.");
        } else {
            $this->rError($errorMessage);
        }       
    }
}
