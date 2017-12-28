<?php

class Mdelivery extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function processDelivery( $dataProc ){
		try {
			$stmt2 = $this->db()->prepare("CALL `_proses_delivery_apk_detail` ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			$stmt2->execute( $dataProc );
		}catch(PDOException $e) {
			$error = $e->getMessage();
		};
	}
	
	public function processDeliveryItem( $dataProc ){
		try {
			$stmt2 = $this->db()->prepare("CALL `_proses_delivery_apk_detail_list` ( ?, ?, ?, ?, ? )");
			$stmt2->execute( $dataProc );
		}catch(PDOException $e) {
			$error = $e->getMessage();
		};
	}
	
	public function getColumnFilterUserDA( $type ){
		$result = null;
		if( Mkey::isDriver( $type ) ){
			$result = "nip_driver";
		}else if( Mkey::isDriverAssistant( $type ) ){
			$result = "nip_asisten_driver";
		};
		return $result;
	}
	
	public function getListDeliveryData( $nip, $type, $id = null ){
		$data = null;
		$filterColumn = $this->getColumnFilterUserDA( $type );
		if( $filterColumn != null ){
			$selectStatement = $this->db()
				->select()
				->from( 'tv_trans_delivery' )
				->where( 'id_status', '=', 2 )
				->where( $filterColumn, '=', $nip );
				
			$stmt = $selectStatement->execute();
			$data = $stmt->fetchAll();
		};
		return $data;
	}
	
	public function getListDeliveryDetailData( $IDP_Delivery, $id = null ){
		$selectStatement = $this->db()
			->select()
			->from( 'tv_trans_delivery_detail' )
			->where( 'idp_delivery', '=', $IDP_Delivery );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
	public function getListDeliveryItemData( $ID_Barang, $id = null ){
		$selectStatement = $this->db()
			->select()
			->from( 'tv_trans_delivery_detail_list_android' )
			->where( 'id_barang', '=', $ID_Barang );
			
		$stmt = $selectStatement->execute();
		$data = $stmt->fetchAll();
		return $data;
	}
	
}