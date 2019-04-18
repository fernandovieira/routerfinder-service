package br.com.ebexs.routerfinderservice.service.impl;


import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.service.GenerateGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateGraphImplTest {

    private List<RouteModel> routeModels;

    @Before
    public void setup() {

        routeModels = new ArrayList<>();

        RouteModel routeModel = new RouteModel();

        routeModel.setFrom("GRU");
        routeModel.setTo("BRC");
        routeModel.setPrice("10");

        routeModels.add(routeModel);

        routeModel = new RouteModel();

        routeModel.setFrom("BRC");
        routeModel.setTo("SCL");
        routeModel.setPrice("5");

        routeModels.add(routeModel);

        routeModel = new RouteModel();

        routeModel.setFrom("GRU");
        routeModel.setTo("CDG");
        routeModel.setPrice("75");

        routeModels.add(routeModel);

    }

    @Test
    public void generateGraph() {

        GenerateGraph generateGraph = new GenerateGraphImpl(routeModels);

        Graph graph = generateGraph.generateGraph();

        Map<Vertex, String> value = new HashMap<>();

        Vertex vertex = new Vertex();
        vertex.setLocale("SCL");
        value.put(vertex, "5");

        Vertex key = new Vertex();
        key.setLocale("BRC");

        Assert.assertEquals(graph.getNode().get(key), value);
    }

    @Test
    public void generateGraphEmpty() {

        GenerateGraph generateGraph = new GenerateGraphImpl(new ArrayList<>());

        Graph graph = generateGraph.generateGraph();

        Assert.assertTrue(graph.getNode().isEmpty());
    }
}