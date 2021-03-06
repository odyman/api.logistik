<?php

class Minbound extends Models {

    public function __construct() {
        parent::__construct();        
    }
    /**
     * get inbound list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getInboundList($kode_unker, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select()
                ->from('tv_trans_inbound_bast')
                ->where('id_status', "=", 2)
                ->where('kode_unker_inbound', "=", $kode_unker)
                ->whereLike('kode_inbound', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("ID_Inbound_BAST", "ASC");
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
                ->from('tv_trans_inbound_bast')
                ->where('id_status', "=", 2)
                ->where('kode_unker_inbound', "=", $kode_unker)
                ->whereLike('kode_inbound', "%$query%")                
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }   

    function getInboundById($id) {
        return $this->db()->select(array('*'))
                ->from("tv_trans_inbound_bast")
                ->where('ID_Inbound_BAST', "=", $id)
                ->where('id_status', "=", 2)
                ->limit(10)
                ->execute()
                ->fetch();
    }

    /**
     * get inbound detail list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getInboundDetailList($ID_Inbound_BAST, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select()
                ->from('tv_trans_inbound_bast_detail')                
                ->where('ID_Inbound_BAST', "=", $ID_Inbound_BAST)
                ->whereLike('no_resi', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("ID_Inbound_BAST", "ASC");
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
                ->from('tv_trans_inbound_bast_detail')
                ->where('ID_Inbound_BAST', "=", $ID_Inbound_BAST)
                ->whereLike('no_resi', "%$query%")                
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }   

    function getInboundDetailInfo($ID_Inbound_BAST, $ID_Barang) {
        return $this->db()->select(array('*'))
                ->from("tv_trans_inbound_bast_detail")
                ->where('ID_Inbound_BAST', "=", $ID_Inbound_BAST)
                ->where('ID_Barang', "=", $ID_Barang)
                ->limit(10)
                ->execute()
                ->fetch();
    }

    /**
     * get inbound detail item list
     * 
     * @param type $perpage
     * @param type $page
     * @param type $query
     * @param type $sort
     * @return array
     */
    public function getInboundDetailItemList($ID_Inbound_BAST, $ID_Barang, $perpage, $page, $query = null, $sort = null) {
    	$queryDb = $this->db()
                ->select()
                ->from('tv_trans_inbound_bast_detail_list')                
                ->where('ID_Inbound_BAST', "=", $ID_Inbound_BAST)
                ->where('ID_Barang', "=", $ID_Barang)
                ->whereLike('kode_barcode', "%$query%");                

        if (empty($sort)) {
            $queryDb->orderBy("ID_Inbound_BAST_Detail_List", "ASC");
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
                ->from('tv_trans_inbound_bast_detail_list')                
                ->where('ID_Inbound_BAST', "=", $ID_Inbound_BAST)
                ->where('ID_Barang', "=", $ID_Barang)
                ->whereLike('kode_barcode', "%$query%")             
                ->execute()
                ->fetch();
        
        $count = $row['row'];

        return $this->gt(true, $data, ['count' => $count, 'page' => $page, 'perpage' => $perpage]);
    }  

    /**
     * post scan qrcode inbound 
     * 
     * @param $dataFunction
     * @return message
     */    
    function scancekQRCodeInbound( $data, & $errorMessage ) {
        $this->db->beginTransaction();        
        try {                       
          //--> Call stored function
          $stmt = $this->db()->prepare("SELECT f_info_qrcode_inbound_apk(".$data['_ID'].",'".$data['_QR']."') AS message");
          $stmt->execute();          
          $result = $stmt->fetch();          
          
          return $result;
        }catch(PDOException $e) {
          $error = $e->getMessage();
          return $error;
        }        
    }

    /**
     * post process simpan data qrcode inbound
     * 
     * @param $dataProc
     * @return message
     */
    public function _saveQRCodeInbound( $data, & $errorMessage ) {  
        //--> Call stored procedure                    
        try {
          $stmt2 = $this->db()->prepare("CALL `_proses_inbound_bast_barcode_save` (?, ?, ?, ?, ?, ?, ?, ?)");        
          $stmt2->execute( $data );              
          $this->db()->commit();              

          return true;
        }catch(PDOException $e) {
          $error = $e->getMessage();              
          $this->db->rollBack();
          return false;
        };                             
    }

    /**
     * post process simpan data qrcode inbound
     * 
     * @param $dataProc
     * @return message
     */
    public function saveQRCodeInbound( $data, & $errorMessage ) {  
        $this->db->beginTransaction();
        $data_s_insert = array(                     
            'createdDate' => date("Y-m-d H:i:s")
        );

        $data_update = array(
            'status_barcode' => 2,                        
            'updatedDate' => date("Y-m-d H:i:s")
        );         

        $data_save = array_merge($data, $data_s_insert);

        try{            
            //--> Insert
            $this->db->insert( array_keys($data_save) )
                    ->into( 'logistik_ttrans_inbound_bast_detail_list' )
                    ->values( array_values($data_save) )
                    ->execute();
            $this->db->commit();
            
            //--> Update status_barcode =2
            $this->updateBarcode( $data_update, $data['kode_barcode'] );            
            
            return true;
        } catch (Exception $ex) {
            $errorMessage = "QRCode failed save!";
            $this->db->rollBack();
            return false;
        }          
    }

    public function updateBarcode( $data, $key_data ) {  
        $this->db->beginTransaction();      
        
        try{
            $this->db->update( $data )
                ->table( 'logistik_tbarcode_list' )
                ->where( 'kode_barcode', '=', $key_data )
                ->execute();
            $this->db->commit();
            return true;
        } catch (Exception $ex) {            
            $this->db->rollBack();
            return false;
        }
    }
}
