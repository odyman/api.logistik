<?php

class Cindex extends Controllers {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getIndex( $request, $response, $args ){
		$response->withJSON( array('status'=>true, 'message' => 'ok') );
		return $response;
	}
	
	public function postAuth( $request, $response, $args ){
		$result = Muser::getInstance()->validateUserLogon( $request->getParam('username'), $request->getParam('password') );
		$response->withJSON( $result );
	}
	
	public function getDate( $request, $response, $args ){
		return $response->withJSON(array('status'=>true,'message'=>'success','serverdate'=>date('Y-m-d H:i:s')));
	}
	
	public function getTime( $request, $response, $args ){
		return $response->withJSON(array('status'=>true,'message'=>'success','servertime'=>time()));
	}
	
	public function postSync( $request, $response, $args ){
		$result = Msync::getInstance()->doSync( $request->getHeader('SMAPINIP'), $request->getHeader('SMAPITYPE'), $request->getParam('data') );
		$response->withJSON( $result );
	}
	/**
     * Ubah Password User
     */
    public function postUbahPassword($request, $response, $args)
    {
        $nip = $request->getHeader('SMAPINIP');
        $passsword = $request->getParam('password');        

        $result    = Muser::getInstance()->ubahPassword($nip[0], $passsword);
        return $response->withJSON($result);
    }
	
}