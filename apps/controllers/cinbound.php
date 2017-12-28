<?php

/**
 * Controller untuk Inbound
 *
 * @author Odyman <odyodyman@yahoo.com>
 */
class Cinbound extends Controllers {

    public function __construct() {
        parent::__construct();
    }

     /**
     * inbound list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getInboundList($request, $response, $args) {
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

        return $response->withJSON(Minbound::getInstance()->getInboundList($kode_unker, $perpage, $page, $query, $sort));        
    }   

    public function getInboundById( $request, $response, $args )
    {
        $id = $args['id'];
        $result = Minbound::getInstance()->getInboundById($id);

        return $response->withJSON($result);
    }

     /**
     * inbound detail list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getInboundDetailList($request, $response, $args) {
    	$ID_Inbound_BAST = "";
    	if($args['id'])
    		$ID_Inbound_BAST = $args['id']; 
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

        return $response->withJSON(Minbound::getInstance()->getInboundDetailList($ID_Inbound_BAST, $perpage, $page, $query, $sort));        
    }   

    public function getInboundDetailInfo( $request, $response, $args )
    {
        $ID_Inbound_BAST = $args['id_inbound_bast'];
        $ID_Barang = $args['id_barang'];
        $result = Minbound::getInstance()->getInboundDetailInfo($ID_Inbound_BAST, $ID_Barang);

        return $response->withJSON($result);
    }

    /**
     * inbound detail item list with page, per_page, query, sort
     * 
     * @param Slim\Http\Request $request
     * @param Slim\Http\Response $response
     * @param type $args
     * @return Slim\Http\Response $response
     */
    public function getInboundDetailItemList($request, $response, $args) {
    	$ID_Inbound_BAST = "";
    	if($args['id_inbound_bast'])
    		$ID_Inbound_BAST = $args['id_inbound_bast']; 
    	$ID_Barang = "";
    	if($args['id_barang'])
    		$ID_Barang = $args['id_barang']; 
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

        return $response->withJSON(Minbound::getInstance()->getInboundDetailItemList($ID_Inbound_BAST, $ID_Barang, $perpage, $page, $query, $sort));        
    }  

    public function scancekQRCodeInbound($request, $response, $args) {            
		$errorMessage = '';     	
        return $response->withJSON(Minbound::getInstance()->scancekQRCodeInbound($request->getParsedBody(), $errorMessage));
    }

    public function saveQRCodeInbound($request, $response, $args) {
        $model = Minbound::getInstance($request);
        $errorMessage = '';
        // return $response->withJSON(Minbound::getInstance()->saveQRCodeInbound($request->getParsedBody(), $errorMessage));
        if (Minbound::getInstance()->saveQRCodeInbound($request->getParsedBody(), $errorMessage))
        {
            $this->rSuccess("Inbound berhasil disimpan.");
        } else {
            $this->rError($errorMessage);
        }       
    }
}
