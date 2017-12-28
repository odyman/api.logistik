<?php

class Muser extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public static function passwordEuy( $username, $password, $pas5 = true ){
		return md5( $username . "-smr-" . ( $pas5 ? md5( $password ) : $password ) );
	}
	
	function getUserData( $username ){
		$username = trim( $username );
		$selectStatement = $this->db()->select(array('ta.user', 'ta.pass', 'ta.NIP', 'ta.type', 'tv.kode_unker'))
						   ->from('tuser_android ta')						   
						   ->join('tv_user_android_list tv', 'ta.ID_User_Driver', '=', 'tv.ID_User_Driver')
						   ->where('ta.user', '=', $username )
						   ->where('ta.status', '=', 1 );
		$stmt = $selectStatement->execute();
		$data = $stmt->fetch();
		return $data;
	}

	function validateUserLogon( $username, $password ){
		$username = trim( $username );
		$password = trim( $password );
		$result = array(
			'status' => false,
			'message' => 'unknow'
		);
		$data = $this->getUserData( $username );
		if( $data ){
			if( Muser::passwordEuy( $username, $data["pass"], false ) == Muser::passwordEuy( $username, $password ) ) {
				$result['token'] = base64_encode( $username . '-exmlindonesia-' . Muser::passwordEuy( $username, $password ) );
				$result['nip'] = $data["NIP"];
				$result['type'] = $data["type"];
				$result['kode_unker'] = $data["kode_unker"];
				$result['status'] = true;
				$result['message'] = 'success';
			}else{
				$result['message'] = 'wrong password or username';
			}
		}else{
			$result['message'] = 'wrong password or username';
		}
		
		return $result;
	}
	
	function checkUserToken( $token, $nip ){
		$result = array(
			'status' 	=> false,
			'username' 	=> null
		);
		$token = base64_decode( $token );
		$token = explode( '-exmlindonesia-', $token );
		if( count( $token ) == 2 ){
			$data = $this->getUserData( $token[0] );
			if( $data ){
				if( Muser::passwordEuy( $token[0], $data["pass"], false ) == $token[1] && $data["NIP"] == $nip ) {
					$result['status'] 	= true;
					$result['username'] = $token[0];
				};
			}
		};
		return $result;
	}

	function validateUserToken( $key, $token, $nip ){
		$status = false;
		if( is_array( $token ) ){
			if( count( $token ) > 0 ){
				$token = $token[0];
			}else{
				$token = "";
			};
		};
		if( Mkey::getInstance()->isValid( $key ) ){
			$checktoken = ( $this->checkUserToken( $token, $nip ) );
			$status = $checktoken['status'];
		};
		return $status;
	}

	/**
     * Merubah password user dengan nip
     * @param string $nip
     * @param string $password
     * @return array
     */
    public function ubahPassword($nip, $password) {
        $password = trim($password);

        $updateStatement = $this->db()->update(array("pass" => MD5($password)))
                ->table("tuser_android")
                ->where("`NIP`", "=", $nip);

        if ($updateStatement->execute()) {
            return array(
                'success' => true,
                'message' => 'Kata sandi berhasil dirubah'
            );
        } 
        
        return array(
            'success' => false,
            'errors' => array(
                'reason' => 'Gagal Merubah Kata Sandi !'
            )
        );
    }
}