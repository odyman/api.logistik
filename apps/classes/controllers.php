<?php

class Controllers extends Singletone {
	
	public $route;

    public function __construct()
    {
        parent::__construct();
    }

    public function getArgument($request, $argumenName)
    {
        if ($this->route == null) {
            $this->route = $request->getAttribute('route');
        }
        return $this->route->getArgument($argumenName);
    }

    public function getTop( $request )
    {
        $top = $request->getQueryParam('top');
        if (  is_null($top)) $top = 0;
        return $top;
    }

    public function view($response, $template, $data = array())
    {
        // var_dump(Routing::$app);
        return Routing::$app->getContainer()->view->render($response, $template, $data);
    }

    public function header_pdf($fileName)
    {
        header("Content-type:application/pdf");
        header("Content-Disposition:attachment;filename='$fileName'");
    }

    public function response_json($data)
    {
        header('Content-Type: application/json');
        echo json_encode($data);
        exit();
    }

    public function rData($status, $data, $extra = array(), $message = "")
    {
        $parseData = array(
            'status'   => $status,
            'message'  => $message,
            'contents' => $data,
        );

        foreach ($extra as $key => $value) {
            $parseData[$key] = $value;
        }

        return $this->response_json($parseData);
    }

    public function rSuccess($message)
    {
        return $this->response_json(array(
            'status'   => true,
            'message'  => $message,
        ));
    }

    public function rError($message = "")
    {
        return $this->response_json(array(
            'status'   => false,
            'message'  => "Error: ". $message,
        ));
    }
	
}