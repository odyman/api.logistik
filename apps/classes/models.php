<?php

class Models extends Singletone {
	protected $db = null;
    protected $configs = null;
    protected $offset = 6;
	
	public function __construct(){
		parent::__construct();
		$configs =  require "./configs.php";
		$this->configs = $configs;
		$database_dsn = "mysql:host=".$configs["db"]["hostname"].";dbname=".$configs["db"]["database"].";charset=utf8";
		$database_usr = $configs['db']['username'];
		$database_pwd = $configs['db']['password'];
				

		$this->db = new \Slim\PDO\Database($database_dsn, $database_usr, $database_pwd);
        $this->db->setAttribute(PDO::FETCH_ASSOC, true);
        $this->db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
        $this->db->setAttribute(PDO::ATTR_STRINGIFY_FETCHES, false);
	}
	
	/**
     * get database connection
     * @return \Slim\PDO\Database
     */
    protected function db() {
        return $this->db;
    }

    public function getVal(&$val, $default = NULL) {
        return isset($val) ? $val : $default;
    }

    /**
     * default view untuk Model query
     * @param string $view
     * @return \Slim\PDO\Statement\SelectStatement
     */
    public function initSql($view, $field = array('*')) {
        return $this->db()
                        ->select($field)
                        ->from($view);
    }

    public function r($status, $data, $extra = array(), $message = "") {
        $parseData = array(
            'status' => $status,
            'message' => $message,
            'contents' => $data,
        );

        foreach ($extra as $key => $value) {
            $parseData[$key] = $value;
        }

        return $parseData;
    }

    /*
     * Generic table template response
     */

    public function gt($type, $data, $extra) {
        
        $paging = [
            "pageNext" => $extra['page'] + 1,
            "pageCurrent" => (int) $extra['page'],
            "pagePrevious" => $extra['page'] - 1,    
            "recordsAfterFilter" => $extra['count'],
            "recordsAfterSearch" => $extra['count'],
            "recordsAll" => $extra['count']
        ];

        if (isset($extra['perpage'])){
            $paging["recordLength"] = (int) $extra['perpage'];
        }
        
        $parseData = array(
            'data' => $data,
            'paging' => $paging
        );

        return $parseData;
    }
}