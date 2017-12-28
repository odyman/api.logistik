<?php

class Mcustomer extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getListCustomer( $kode_customer, $id = null ){
		$selectStatement = $this->db()
			->select()
			->from( 'tv_browseref_order_entry_detail' )
			->where( 'kode_customer', '=', $kode_customer );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
}