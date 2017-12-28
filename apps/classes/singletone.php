<?php

class Singletone {
    /**
     * @var Singletone
     */
    protected static $instances;

    public function __construct()
    {
    }

    /**
     * @return Singletone
     */
    public static function getInstance($construct=null)
    {
        $class = get_called_class();
        if ( ! isset(self::$instances[$class])) {
            if($construct)
                self::$instances[$class] = new $class($construct);
            else
                self::$instances[$class] = new $class();
        }
        return self::$instances[$class];
    }

    public static function model()
    {
        return self::getInstance();
    }
	
	final private function __clone() {
		
	}


}