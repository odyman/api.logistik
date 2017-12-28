<?php

class Mkey extends Models {
	
	public function __construct(){
		parent::__construct();
	}
	
	public function isValid( $key ){
		if( is_array( $key ) ){
			if( count( $key ) > 0 ){
				$key = $key[0];
			}else{
				$key = "";
			};
		};
		return ( ( $key == $this->configs['api']['key'] ) ? true : false );
	}
	
	public static function clearNIP( $nip ){
		return ( count( $nip ) > 0 ? $nip[0] : null );
	}
	
	//1 = driver, 2 = asisten, 3 = staff
	public static function clearType( $type ){
		return ( count( $type ) > 0 ? $type[0] : null );
	}
	public static function isDriver( $type ){
		return ( Mkey::clearType( $type ) == 1 ? true : false );
	}
	public static function isDriverAssistant( $type ){
		return ( Mkey::clearType( $type ) == 2 ? true : false );
	}
	public static function isStaff( $type ){
		return ( Mkey::clearType( $type ) == 3 ? true : false );
	}
	
}