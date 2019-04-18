package br.com.ebexs.routerfinderservice.service.impl;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.service.GenerateGraph;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GenerateGraphImpl implements GenerateGraph {

    private List<String> vertexs = new ArrayList<>();
    private List<Vertex> vertexList = new ArrayList<>();
    private List<RouteModel> routeModels;

    public GenerateGraphImpl(List<RouteModel> routeModels) {
        this.routeModels = routeModels;
        generateVertex();
    }

    private List<Vertex> generateVertex() {

        generateVertexFrom(routeModels);
        generateVertexTo(routeModels);

        vertexFromStringToVertex();

        return vertexList;
    }

    private void vertexFromStringToVertex() {
        vertexs
                .forEach( stringVertex -> {
                    Vertex vertex = new Vertex(stringVertex);
                    vertexList.add(vertex);
        });
    }

    private void generateVertexTo(List<RouteModel> routeModels) {
        routeModels
                .stream()
                .sorted(Comparator.comparing(RouteModel::getFrom))
                .map(RouteModel::getTo)
                .distinct()
                .forEach( vertex -> {
                    if (!vertexs.contains(vertex)) vertexs.add(vertex);
                });
    }

    private void generateVertexFrom(List<RouteModel> routeModels) {
        routeModels
                .stream()
                .sorted(Comparator.comparing(RouteModel::getFrom))
                .map(RouteModel::getFrom)
                .distinct()
                .forEach(vertexs::add);
    }


    @Override
    public Graph generateGraph() {
        Graph graph = new Graph();
        vertexList.forEach( vertexFrom -> {
            graph.addGraph(vertexFrom, generateValueMapToGraph(routeModels, vertexFrom));
        });

        return graph;
    }

    private Map<Vertex, String> generateValueMapToGraph(List<RouteModel> routeModels, Vertex vertexFrom) {
        return routeModels.stream()
            .filter(routeModel -> routeModel.getFrom().equals(vertexFrom.getLocale()))
                .collect(Collectors.toMap(getRouteModelVertexFunction(), RouteModel::getPrice));
    }

    private Function<RouteModel, Vertex> getRouteModelVertexFunction() {
        return x-> {
            Vertex vertex = new Vertex(x.getTo());
            return vertex;
        };
    }
}
