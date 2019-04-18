package br.com.ebexs.routerfinderservice.service.impl;


import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ReadCSV implements ReadRouteRegistry<RouteModel> {

    @Override
    public List<RouteModel> read(String path) throws ResourceRouterUnavailableException {

        List<RouteModel>  routeModels = new ArrayList<>();

        if (path == null || path.isEmpty()) {
            throw new ResourceRouterUnavailableException("Path is not valid");
        }

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 3) {
                    throw new ResourceRouterUnavailableException("Invalid format");
                } else {
                    routeModels.add(buildModel(fields));
                }
            }

        } catch(IOException exception) {
            throw new ResourceRouterUnavailableException("Can't open file", exception);
        }

        return routeModels;
    }

    private RouteModel buildModel(String[] fields) {
        RouteModel routeModel = new RouteModel();
        routeModel.setFrom(fields[0]);
        routeModel.setTo(fields[1]);
        routeModel.setPrice(fields[2]);
        return routeModel;
    }
}
