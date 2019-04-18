package br.com.ebexs.routerfinderservice.service.impl;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteFind;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.exception.CantReachDestination;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateCheapestWayImplTest {

    private Map<Vertex, Map<Vertex, String>> graph;
    private Map<Vertex, String> vertexTo;
    private Graph exampleFile = new Graph();

    @Before
    public void setup() {

        Vertex vertexFrom = new Vertex("GRU");

        Map<Vertex, String> vertexTo = new HashMap<>();

        vertexTo.put(new Vertex("BRC"), "10");
        vertexTo.put(new Vertex("SCL"), "20");
        vertexTo.put(new Vertex("CDG"), "75");
        vertexTo.put(new Vertex("ORL"), "56");

        exampleFile.addGraph(vertexFrom, vertexTo);

        vertexFrom = new Vertex("BRC");

        vertexTo = new HashMap<>();

        vertexTo.put(new Vertex("SCL"), "5");

        exampleFile.addGraph(vertexFrom, vertexTo);

        vertexFrom = new Vertex("ORL");

        vertexTo = new HashMap<>();

        vertexTo.put(new Vertex("CDG"), "5");

        exampleFile.addGraph(vertexFrom, vertexTo);

        vertexFrom = new Vertex("SCL");

        vertexTo = new HashMap<>();

        vertexTo.put(new Vertex("ORL"), "20");

        exampleFile.addGraph(vertexFrom, vertexTo);

    }

    @Test
    public void shouldSpend40() throws Exception {
        CalculateCheapestWay ccW = new CalculateCheapestWayImpl();

        Vertex from= new Vertex();
        from.setLocale("GRU");

        Vertex to= new Vertex();
        to.setLocale("CDG");

        RouteFind result = ccW.doTheMath(exampleFile, from, to);

        Assert.assertEquals(result.getTotal(), 40);

    }


    @Test(expected = CantReachDestination.class)
    public void shouldThrowException() throws Exception {
        CalculateCheapestWay ccW = new CalculateCheapestWayImpl();

        Vertex from= new Vertex();
        from.setLocale("GRU");

        Vertex to= new Vertex();
        to.setLocale("XXX");

        RouteFind result = ccW.doTheMath(exampleFile, from, to);

    }

    @Test(expected = CantReachDestination.class)
    public void shouldThrowExceptionInvalidOrigin() throws Exception {
        CalculateCheapestWay ccW = new CalculateCheapestWayImpl();

        Vertex from= new Vertex();
        from.setLocale("AAA");

        Vertex to= new Vertex();
        to.setLocale("CDG");

        RouteFind result = ccW.doTheMath(exampleFile, from, to);

    }
}
