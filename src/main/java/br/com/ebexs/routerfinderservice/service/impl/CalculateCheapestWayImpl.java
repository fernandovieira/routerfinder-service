package br.com.ebexs.routerfinderservice.service.impl;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteFind;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.exception.CantReachDestination;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;


import java.util.Map;


public class CalculateCheapestWayImpl implements CalculateCheapestWay {

    @Override
    public RouteFind doTheMath(Graph graph, Vertex from, Vertex to) throws Exception {

        Map<Vertex, String> actualVertex = graph.getNode().get(from);

        RouteFind routeFindModel = new RouteFind();
        routeFindModel.setRoute(from.getLocale());
        RouteFind routeFind = new RouteFind();

        while (!to.equals(routeFindModel.getNextVertx())) {
            if (checkIfReachTheEnd(actualVertex, routeFind)) return routeFind;
            for (Vertex key : actualVertex.keySet()) {
                String value = checkIfItstheMinorThanPreviousOne(actualVertex, routeFindModel, key);
                setLastChanceToDestination(to, routeFindModel, routeFind, key, value);
            }
            actualVertex = setAcumulator(graph, routeFindModel);
        }


        return routeFindModel;

    }

    private String checkIfItstheMinorThanPreviousOne(Map<Vertex, String> actualVertex, RouteFind routeFindModel, Vertex key) {
        String value = actualVertex.get(key);
        if ((routeFindModel.getPrice().compareTo(value) > 0) || ("0".equals(routeFindModel.getPrice()))) {
            routeFindModel.setPrice(value);
            routeFindModel.setNextVertex(key);
        }
        return value;
    }

    private boolean checkIfReachTheEnd(Map<Vertex, String> actualVertex, RouteFind routeFind) throws Exception {
        if ((actualVertex == null) || (actualVertex.isEmpty())) {

            if (routeFind.getWalkThrough()) {
                return true;
            } else {
                throw new CantReachDestination("I Haven't found anything to this route");
            }
        }
        return false;
    }

    private void setLastChanceToDestination(Vertex to, RouteFind routeFindModel, RouteFind routeFind, Vertex key, String value) {
        if (key.getLocale().equals(to.getLocale())) {
           routeFind.setTotal(routeFindModel.getTotal() + Integer.parseInt(value));
           routeFindModel.getRoute().forEach(route -> routeFind.setRoute(route));
           routeFind.setRoute(key.getLocale());
           routeFind.setWalkThrough(true);
        }
    }

    private Map<Vertex, String> setAcumulator(Graph graph, RouteFind routeFindModel) {
        Map<Vertex, String> actualVertex;

        routeFindModel.setTotal(routeFindModel.getTotal() + Integer.parseInt(routeFindModel.getPrice()));
        routeFindModel.setPrice("0");
        if (routeFindModel.getNextVertx() != null) {
            routeFindModel.setRoute(routeFindModel.getNextVertx().getLocale());
            actualVertex = graph.getNode().get(routeFindModel.getNextVertx());
        } else {
            actualVertex = null;
        }
        return actualVertex;
    }
}