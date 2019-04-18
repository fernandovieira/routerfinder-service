package br.com.ebexs.routerfinderservice.service;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteFind;
import br.com.ebexs.routerfindermodel.model.Vertex;

import java.util.Map;

public interface CalculateCheapestWay {

    RouteFind doTheMath(Graph graph, Vertex from, Vertex to) throws Exception;
}
