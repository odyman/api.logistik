<?php

class Msync extends Models {

  private $instanPickup = null;
  private $instanDelivery = null;

  public function __construct(){
    parent::__construct();
    $this->instanPickup = Mpickup::getInstance();
    $this->instanDelivery = Mdelivery::getInstance();
  }

  public function setForSyncData( $nip, $type, $data ){
    $status1 = false;
    $status2 = false;
    if( isset( $data['pickup'] ) ){
      if( isset( $data['pickup']['detail'] ) && isset( $data['pickup']['item'] ) ){
        if( isset( $data['pickup']['detail'] ) ){
          $tempDetail = $data['pickup']['detail'];
          foreach( $tempDetail as $k => $v ) {

            /* `db_access_logistic`.`_proses_pickup_entry_apk_detail`(
              <{ID_Pickup INT}>,
              <{tipe_pickup INT}>,
              <{ID_Order INT}>,
              <{ID_Outgoing INT}>,
              <{no_resi VARCHAR(50)}>,
              <{tgl_pickup DATE}>,
              <{jenis_kirim VARCHAR(50)}>,
              <{nama_barang VARCHAR(50)}>,
              <{nama_satuan VARCHAR(50)}>,
              <{kuantitas INT}>,
              <{ID_Customer_Location INT}>,
              <{volume FLOAT}>,
              <{berat_kg FLOAT}>); */
            $this->instanPickup->insertDetil(array(
              $v['ID_Pickup'],
              $v['tipe_pickup'],
              $v['ID_Order'],
              $v['ID_Outgoing'],
              $v['no_resi'],
              $v['tgl_pickup'],
              $v['jenis_kirim'],
              $v['nama_barang'],
              $v['nama_satuan'],
              $v['kuantitas'],
              $v['ID_Customer_Location'],
              $v['volume'],
              $v['berat_kg']
            ));
          };
        };
        if( isset( $data['pickup']['item'] ) ){
          $tempItem = $data['pickup']['item'];
          foreach( $tempItem as $k => $v ) {
            $this->instanPickup->insertItem(array(
              $v['ID_Pickup'],
              $v['ID_Order'],
              $v['no_resi'],
              $v['kode_barcode'],
              $v['berat_kg'],
              $v['volume'],
              $v['keterangan'],
              $v['latitude'],
              $v['longitude']
            ));
          };
        };
        $status1 = true;
      };
    };
    if( isset( $data['delivery'] ) ){
      if( isset( $data['delivery']['processitems'] ) ){
        $tempDetail = $data['delivery']['processitems'];
        foreach( $tempDetail as $k => $v ) {
          $this->instanDelivery->processDeliveryItem(array(
            $v['IDP_Delivery_Detail'],
            $v['kode_barcode'],
            $v['latitude'],
            $v['longitude'],
            $nip
          ));
        };
      };
      if( isset( $data['delivery']['process'] ) ){
        $tempDetail = $data['delivery']['process'];
        foreach( $tempDetail as $k => $v ) {
          $this->instanDelivery->processDelivery(array(
            $v['IDP_Delivery_Detail'],
            $v['ID_Barang'],
            $v['tgl_terima'],
            $v['ID_Status_Delivery'],
            $v['delivery_remark'],
            $v['latitude'],
            $v['longitude'],
            $v['image'],
            $nip
          ));
        };
      };
      $status2 = true;
    };
    return ( $status2 && $status1 ? true : false );
  }

  public function getForSyncData( $nip, $type, $data ){
    $status = $this->setForSyncData( $nip, $type, $data );

    $result = array(
      'pickup'	=> array(
        'order' 	=> array(),
        'detail' 	=> array(),
        'item' 		=> array(),
        'customer' 	=> array(),
        'barcode' 	=> array(
          'available' => array()
        ),
        'jeniskirim' => array()
      ),
      'delivery'	=> array(
        'order' 	=> array(),
        'detail' 	=> array(),
        'item' 		=> array(),
        'status'	=> array()
      ),
      'syncdate'	=> date('Y-m-d H:i:s'),
      'syncspan'	=> time(),
      'status'	=> $status
    );

    //pickup - start
    $pickup = $this->instanPickup->getListPickupData( $nip, $type );
    $cstbak = array();
    if( $pickup ){
      foreach( $pickup as $v1 ) {
        $detail = $this->instanPickup->getListPickupEntryDetailData( $v1['ID_Pickup'], $v1['ID_Order'] );
        if( $detail ){
          foreach( $detail as $v2 ) {
            $item = $this->instanPickup->getListPickupEntryItemData( $v1['ID_Pickup'], $v2['no_resi'] );
            if( $item ) {
              $result['pickup']['item'] = array_merge( $result['pickup']['item'], $item );
            };
            $result['pickup']['detail'][] = $v2;
          };
        };
        if( !in_array( $v1['kode_customer'], $cstbak ) ) {
          $cstbak[] = $v1['kode_customer'];
          $cstlist  = Mcustomer::getInstance()->getListCustomer( $v1['kode_customer'] );
          if( $cstlist ){
            $result['pickup']['customer'] = array_merge( $result['pickup']['customer'], $cstlist );
          };
        };
        $result['pickup']['order'][] = $v1;
      };
    };
    $barcode = Mbarcode::getInstance()->getListBarcodeForDriver( $nip );
    if( $barcode ){
      $result['pickup']['barcode']['available'] = $barcode;
    };
    $jeniskirim = Mjeniskirim::getInstance()->getListJenisKirim();
    if( $jeniskirim ){
      $result['pickup']['jeniskirim'] = $jeniskirim;
    };
    //pickup - end

    //delivery - start
    $delivery = $this->instanDelivery->getListDeliveryData( $nip, $type );
    if( $delivery ){
      foreach( $delivery as $v1 ) {
        $detail = $this->instanDelivery->getListDeliveryDetailData( $v1['IDP_Delivery'] );
        if( $detail ){
          foreach( $detail as $v2 ) {
            $item = $this->instanDelivery->getListDeliveryItemData( $v2['ID_Barang'] );
            if( $item ) {
              $result['delivery']['item'] = array_merge( $result['delivery']['item'], $item );
            };
            $result['delivery']['detail'][] = $v2;
          };
        };
        $result['delivery']['order'][] = $v1;
      };
    };
    $deliverystatus = Mdeliverystatus::getInstance()->getListStatus();
    if( $deliverystatus ){
      $result['delivery']['status'] = $deliverystatus;
    };
    //delivery - end

    return $result;
  }

  public function doSync( $nip, $type, $data ){
    $nip 	= Mkey::clearNIP( $nip );
    $data	= ( !is_array( $data ) ? @json_decode( $data, true ) : $data );
    $result = $this->getForSyncData( $nip, $type, $data );
    return $result;
  }
}