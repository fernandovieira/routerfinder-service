package br.com.ebexs.routerfinderservice.exception;

public class CantReachDestination extends Exception {

    public CantReachDestination(String message) {
        super(message);
    }

    public CantReachDestination(String message, Throwable cause){
        super(message, cause);
    }
}
