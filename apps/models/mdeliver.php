<?php

class Mdeliver extends Models {

    public function __construct() {
        parent::__construct();        
    }
    /**
     * get delivery list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getDeliveryList($kode_unker, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select()
                ->from('tv_trans_delivery')
                ->where('id_status', "=", 1)
                // ->where('kode_unker_delivery', "=", $kode_unker)
                ->whereLike('kode_delivery', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("IDP_Delivery", "ASC");
        } else {
            $sortType = "ASC";
            if ($sort[0] == "-") {
                $sortType = "DESC";
                $sort = ltrim($sort, '-');
            }
            $queryDb->orderBy($sort, $sortType);
        }
        
        $data = $queryDb->limit($perpage, $perpage * ($page-1))
                                ->execute()
                                ->fetchAll();
        $row = $this->db()
                ->select()
                ->count($column = '*', 'row')
                ->from('tv_trans_delivery')
                ->where('id_status', "=", 1)
                // ->where('kode_unker_delivery', "=", $kode_unker)
                ->whereLike('kode_delivery', "%$query%")                
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }   

    function getDeliveryById($id) {
        return $this->db()->select(array('*'))
                ->from("tv_trans_delivery")
                ->where('IDP_Delivery', "=", $id)
                ->where('id_status', "=", 1)
                ->limit(10)
                ->execute()
                ->fetch();
    }

    /**
     * get delivery detail list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getDeliveryDetailList($IDP_Delivery, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select(array('IDP_Delivery_Detail',
								'IDP_Delivery',
								'ID_Barang',
								'kode_AWB',
								'no_resi',
								'jenis_barang',
								'jenis_kirim',
								'nama_layanan',
								'nama_pengirim',
								'nama_penerima',
								'nama_lokasi_cust_penerima',
								'nama_kota_destination',
								'nama_lokasi_destination',
								'kode_zone_destination',
								'alamat_destination',
								'telp_destination',
								'fax_destination',
								'nama_barang',
								'kuantitas',
								'nama_satuan',
								'berat_kg',
								'volume',
								'payment_method',
								'latitude_real',
								'longitude_real',
								'latitude',
								'longitude',
								'nama_lengkap_assign',
								'ID_Status_Delivery',
								'status_delivery',
								'tgl_terima',
								'delivery_remark',
								'check_delivery',
								'ID_Status_Barang',
								'status_barang'))
                ->from('tv_trans_delivery_detail')                
                ->where('IDP_Delivery', "=", $IDP_Delivery)
                ->whereLike('no_resi', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("IDP_Delivery_Detail", "ASC");
        } else {
            $sortType = "ASC";
            if ($sort[0] == "-") {
                $sortType = "DESC";
                $sort = ltrim($sort, '-');
            }
            $queryDb->orderBy($sort, $sortType);
        }
        
        $data = $queryDb->limit($perpage, $perpage * ($page-1))
                                ->execute()
                                ->fetchAll();
        $row = $this->db()
                ->select()
                ->count($column = '*', 'row')
                ->from('tv_trans_delivery_detail')
                ->where('IDP_Delivery', "=", $IDP_Delivery)
                ->whereLike('no_resi', "%$query%")                
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }   

    function getDeliveryDetailInfo($IDP_Delivery_Detail) {
        return $this->db()->select(array('IDP_Delivery_Detail',
								'IDP_Delivery',
								'ID_Barang',
								'kode_AWB',
								'no_resi',
								'jenis_barang',
								'jenis_kirim',
								'nama_layanan',
								'nama_pengirim',
								'nama_penerima',
								'nama_lokasi_cust_penerima',
								'nama_kota_destination',
								'nama_lokasi_destination',
								'kode_zone_destination',
								'alamat_destination',
								'telp_destination',
								'fax_destination',
								'nama_barang',
								'kuantitas',
								'nama_satuan',
								'berat_kg',
								'volume',
								'payment_method',
								'latitude_real',
								'longitude_real',
								'latitude',
								'longitude',
								'nama_lengkap_assign',
								'ID_Status_Delivery',
								'status_delivery',
								'tgl_terima',
								'delivery_remark',
								'check_delivery',
								'ID_Status_Barang',
								'status_barang'))
                ->from("tv_trans_delivery_detail")
                ->where('IDP_Delivery_Detail', "=", $IDP_Delivery_Detail)                
                ->limit(10)
                ->execute()
                ->fetch();
    }

    /**
     * get delivery detail item list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getDeliveryDetailItemList($IDP_Delivery, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select()
                ->from('tv_trans_delivery_detail_list')                
                ->where('IDP_Delivery', "=", $IDP_Delivery)                
                ->whereLike('kode_barcode', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("IDP_Delivery_Detail_List", "ASC");
        } else {
            $sortType = "ASC";
            if ($sort[0] == "-") {
                $sortType = "DESC";
                $sort = ltrim($sort, '-');
            }
            $queryDb->orderBy($sort, $sortType);
        }
        
        $data = $queryDb->limit($perpage, $perpage * ($page-1))
                                ->execute()
                                ->fetchAll();
        $row = $this->db()
                ->select()
                ->count($column = '*', 'row')
                ->from('tv_trans_delivery_detail_list')                
                ->where('IDP_Delivery', "=", $IDP_Delivery)                
                ->whereLike('kode_barcode', "%$query%")             
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }  

    /**
     * post process simpan databarcode
     * 
     * @param $dataProc
     * @return message
     */

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

	/**
     * post scan qrcode delivery 
     * 
     * @param $dataProc
     * @return message
     */    
    function scancekQRCodeDelivery( $data, & $errorMessage ) {
        $this->db->beginTransaction();        
        try {                       
          //--> Call stored function
          $stmt = $this->db()->prepare("SELECT f_info_qrcode_delivery_apk(".$data['_ID'].",'".$data['_QR']."')  AS message");
          $stmt->execute();          
          $result = $stmt->fetch();          
          $data = array_merge($result, $this->get_barang_detail($data['_ID'], $data['_QR']));

          return $data;

        }catch(PDOException $e) {
          $error = $e->getMessage();
          return $error;
        }        
    }

    function get_barang_detail($id, $qrcode){

        $sql = "SELECT c.ID_Barang_Detail as ID_Barang_Detail
                FROM `logistik_ttrans_delivery` a
                LEFT JOIN `logistik_ttrans_delivery_detail` b ON a.IDP_Delivery = b.IDP_Delivery
                LEFT JOIN `logistik_tbarang_detail` c ON b.ID_Barang = c.ID_Barang
                WHERE a.IDP_Delivery =:_ID  AND c.kode_barcode =:_QR ";

        $stmt = $this->db()->prepare($sql);
        $stmt->bindParam(":_ID", $id);
        $stmt->bindParam(":_QR", $qrcode);

        $stmt->execute();
        return $stmt->fetch();
    }
    /**
     * post process simpan data qrcode delivery
     * 
     * @param $dataProc
     * @return message
     */
    public function _saveQRCodeDelivery( $data, & $errorMessage ) {
        //--> Call stored procedure
        try {
          $stmt2 = $this->db()->prepare("CALL _proses_delivery_barcode_save(?, ?, ?)");        
          $stmt2->execute( $data );
          $this->db->commit();

          return true;
        }catch(PDOException $e) {
          $error = $e->getMessage();
          $this->db->rollBack();
          return false;
        };         
    }

    /**
     * post process simpan data qrcode delivery
     * 
     * @param $dataProc
     * @return message
     */
    public function saveQRCodeDelivery( $data, & $errorMessage ) {  
        $this->db->beginTransaction();
        $data_s_insert = array(                     
            'createdDate' => date("Y-m-d H:i:s")
        );       

        $data_save = array_merge($data, $data_s_insert);

        try{            
            //--> Insert
            $this->db->insert( array_keys($data_save) )
                    ->into( 'logistik_ttrans_delivery_detail_list' )
                    ->values( array_values($data_save) )
                    ->execute();
            $this->db->commit();   
            
            return true;
        } catch (Exception $ex) {
            $errorMessage = "QRCode failed save!";
            $this->db->rollBack();
            return false;
        }          
    }

}
