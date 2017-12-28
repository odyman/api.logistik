<?php

class Mjeniskirim extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function getListJenisKirim( $id = null ){
		$selectStatement = $this->db()
			->select()
			->from( 'logistik_tref_jenis_kirim' );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
}