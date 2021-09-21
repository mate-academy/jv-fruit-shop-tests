package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.BalanceHandler;
import core.basesyntax.service.activity.PurchaseHandler;
import core.basesyntax.service.activity.ReturnHandler;
import core.basesyntax.service.activity.SupplyHandler;
import core.basesyntax.service.minorservices.GenerateReportService;
import core.basesyntax.service.minorservices.GenerateReportServiceImpl;
import core.basesyntax.service.minorservices.ReaderService;
import core.basesyntax.service.minorservices.ReaderServiceImpl;
import core.basesyntax.service.strategy.ActivityStrategy;
import core.basesyntax.service.strategy.ActivityStrategyImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FruitsShopServiceTest {
    private static FruitsShopService fruitsShopService;
    private static File file;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @BeforeClass
    public static void beforeClass() {
        Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("b", new BalanceHandler());
        activityHandlerMap.put("p", new PurchaseHandler());
        activityHandlerMap.put("s", new SupplyHandler());
        activityHandlerMap.put("r", new ReturnHandler());
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        ReaderService readerService = new ReaderServiceImpl();
        GenerateReportService generateReportServiceService = new GenerateReportServiceImpl();
        fruitsShopService = new FruitsShopServiceImpl(activityStrategy,
                readerService, generateReportServiceService);
    }

    @Before
    public void setUp() {
        try {
            file = folder.newFile("testFile.txt");
        } catch (IOException ioe) {
            System.err.println("Can't create temporary test file in "
                    + this.getClass().getSimpleName());
        }
    }

    @Test
    public void createReport_validData_Ok() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,100" + System.lineSeparator()
                    + "p,banana,13" + System.lineSeparator()
                    + "r,apple,10" + System.lineSeparator()
                    + "p,apple,20" + System.lineSeparator()
                    + "p,banana,5" + System.lineSeparator()
                    + "s,banana,50");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + file.getName(), e);
        }
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertEquals(expected, fruitsShopService.createReport(file.getPath()));
    }

    @Test(expected = RuntimeException.class)
    public void createReport_invalidAmount_NotOk() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,-100" + System.lineSeparator()
                    + "p,banana,13" + System.lineSeparator()
                    + "r,apple,10" + System.lineSeparator()
                    + "p,apple,20" + System.lineSeparator()
                    + "p,banana,5" + System.lineSeparator()
                    + "s,banana,50");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + file.getName(), e);
        }
        fruitsShopService.createReport(file.getPath());
    }

    @Test(expected = RuntimeException.class)
    public void createReport_invalidLineLength_NotOk() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,100,p" + System.lineSeparator()
                    + "banana,13" + System.lineSeparator()
                    + "r,apple,10" + System.lineSeparator()
                    + "p,apple,20" + System.lineSeparator()
                    + "p,banana,5" + System.lineSeparator()
                    + "s,banana,50");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + file.getName(), e);
        }
        fruitsShopService.createReport(file.getPath());
    }

    @Test(expected = RuntimeException.class)
    public void createReport_invalidValues_NotOk() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("Some,invalid,200" + System.lineSeparator()
                    + "value,here,20");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + file.getName(), e);
        }
        fruitsShopService.createReport(file.getPath());
    }

    @Test(expected = RuntimeException.class)
    public void createReport_emptyFile_NotOk() {
        fruitsShopService.createReport(file.getPath());
    }

    @Test(expected = RuntimeException.class)
    public void createReport_invalidFilePath_NotOk() {
        fruitsShopService.createReport("Invalid file path");
    }
}
