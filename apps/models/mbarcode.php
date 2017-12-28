<?php

class Mbarcode extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getListBarcodeForDriver( $nip ){
		$selectStatement = $this->db()
			->select()
			->from( 'tv_available_barcode_list' )
			->where( 'nip', '=', $nip );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
}