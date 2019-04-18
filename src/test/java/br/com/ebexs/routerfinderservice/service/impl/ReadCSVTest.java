package br.com.ebexs.routerfinderservice.service.impl;

import br.com.ebexs.routerfindermodel.model.RouteModel;

import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadCSVTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private final static String filePrefix = "temporaryFile";
    private final static String fileSuffix = ".csv";
    private File file;
    private ReadRouteRegistry readRouteRegistry;


    @Before
    public void setUp() throws IOException {


        File folder = tempFolder.newFolder();

        file = tempFolder.newFile(filePrefix + fileSuffix);

        FileUtils.write(file, "GRU,BRC,10", "UTF-8");

        readRouteRegistry = new ReadCSV();
    }

    @Test
    public void shouldReadFileAndReturnAString() throws ResourceRouterUnavailableException {
        RouteModel routeModel = new RouteModel();

        List<RouteModel> routeModels = readRouteRegistry.read(file.getAbsoluteFile().toString());

        routeModel.setFrom("GRU");
        routeModel.setTo("BRC");
        routeModel.setPrice("10");
        routeModels.add(routeModel);

        Assert.assertEquals(routeModel.getFrom(), routeModels.get(0).getFrom());
        Assert.assertEquals(routeModel.getTo(), routeModels.get(0).getTo());
        Assert.assertEquals(routeModel.getPrice(), routeModels.get(0).getPrice());
    }

    @Test(expected=ResourceRouterUnavailableException.class)
    public void shouldThrowResourceRouterUnavailableExceptionByInvalidPath() throws ResourceRouterUnavailableException {
      List<RouteModel> routeModels = readRouteRegistry.read("");
    }

    @Test(expected=ResourceRouterUnavailableException.class)
    public void shouldThrowResourceRouterUnavailableExceptionPathDoesntExist() throws ResourceRouterUnavailableException {
        List<RouteModel> routeModels = readRouteRegistry.read("/usr/notexist/file.csv");
    }
}
