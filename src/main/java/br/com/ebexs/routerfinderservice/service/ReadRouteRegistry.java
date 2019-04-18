package br.com.ebexs.routerfinderservice.service;

import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;

import java.util.List;

public interface ReadRouteRegistry<T>  {

    List<T> read(String path) throws ResourceRouterUnavailableException;
}
