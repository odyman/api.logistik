<?php

class Mdeliverystatus extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getListStatus( $id = null ){
		$selectStatement = $this->db()
			->select()
			->from( 'logistik_tref_status_delivery' );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
}