package br.com.ebexs.routerfinderservice.exception;

import java.io.IOException;

public class ResourceRouterUnavailableException extends IOException {

    public ResourceRouterUnavailableException(String message) {
        super(message);
    }

    public ResourceRouterUnavailableException(String message, Throwable cause){
        super(message, cause);
    }
}
