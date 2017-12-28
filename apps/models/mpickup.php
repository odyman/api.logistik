<?php

class Mpickup extends Models {

  public function __construct(){
    parent::__construct();
  }

  public function insertDetil( $dataDetail ){
    try {
      //`db_access_logistic`.`_proses_pickup_entry_apk_detail`(<{_ID_Pickup INT}>, <{_tipe_pickup INT}>, <{_ID_Order INT}>, <{_ID_Outgoing INT}>, <{_no_resi VARCHAR(50)}>, <{_tgl_pickup DATE}>, <{_jenis_kirim VARCHAR(50)}>, <{_nama_barang VARCHAR(50)}>, <{_nama_satuan VARCHAR(50)}>, <{_kuantitas INT}>, <{_ID_Customer_Location INT}>, <{_volume FLOAT}>, <{_berat_kg FLOAT}>);
      $stmt2 = $this->db()->prepare("CALL `_proses_pickup_entry_apk_detail`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      $stmt2->execute( $dataDetail );
    }catch(PDOException $e) {
      $error = $e->getMessage();
    };
  }

  public function insertItem( $dataItem ){
    try {
      $stmt2 = $this->db()->prepare("CALL `_proses_pickup_entry_apk_detail_list` ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");
      $stmt2->execute( $dataItem );
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

  public function getListPickupData( $nip, $type, $id = null ){
    $data = null;
    $filterColumn = $this->getColumnFilterUserDA( $type );
    if( $filterColumn != null ){
      $selectStatement = $this->db()
        ->select()
        ->from( 'tv_trans_pickup_order' )
        ->where( 'id_status', '=', 2 )
        ->where( $filterColumn, '=', $nip );

      $stmt = $selectStatement->execute();
      $data = $stmt->fetchAll();
    };
    return $data;
  }

  //list entry detail - start
  public function getListPickupEntryDetailData( $ID_Pickup, $ID_Order, $id = null ){
    $selectStatement = $this->db()
      ->select()
      ->from( 'tv_trans_pickup_detail' )
      ->where( 'id_pickup', '=', $ID_Pickup )
      ->where( 'id_order', '=', $ID_Order );

    $stmt = $selectStatement->execute();
    $data = $stmt->fetchAll();
    return $data;
  }
  //list entry detail - end

  //list entry item - start
  public function getListPickupEntryItemData( $ID_Pickup, $no_resi, $id = null ){
    $selectStatement = $this->db()
      ->select()
      ->from( 'tv_trans_pickup_detail_list' )
      ->where( 'id_pickup', '=', $ID_Pickup )
      ->where( 'no_resi', '=', $no_resi );

    $stmt = $selectStatement->execute();
    $data = $stmt->fetchAll();
    return $data;
  }
  //list entry item - end

}