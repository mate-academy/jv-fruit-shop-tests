package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.BalanceHandler;
import core.basesyntax.service.type.service.OperationHandler;
import core.basesyntax.service.type.service.PurchaseHandler;
import core.basesyntax.service.type.service.ReturnHandler;
import core.basesyntax.service.type.service.SupplyHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static OperationStrategy operationStrategy;
    private static final String EMPTY_PATH = "";
    private static final String CORRECT_PATH_FROM = "src/test/resources/filesFruitShop.csv";
    private static final String CORRECT_PATH_TO = "src/test/resources/report_fruit_shop.csv";
    private static final String EXPECTED = "src/test/resources/expected.csv";
    private static final String INCORRECT_EXPECTED = "src/test/resources/incorrect_expected.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler = new HashMap<>();
        mapTypeHandler.put(FruitRecordDto.Activities.BALANCE, new BalanceHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.RETURN, new ReturnHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(mapTypeHandler);
        reportService = new ReportServiceImpl(operationStrategy);
    }

    @Test
    public void getReport_correctPath_Ok() throws IOException {
        String expected = Files.readString(Path.of(EXPECTED));
        reportService.getReport(CORRECT_PATH_FROM, CORRECT_PATH_TO);
        String actual = Files.readString(Path.of(CORRECT_PATH_TO));
        assertEquals(expected,actual);
    }

    @Test
    public void getReport_incorrectPath_NotOk() throws IOException {
        String expected = Files.readString(Path.of(INCORRECT_EXPECTED));
        reportService.getReport(CORRECT_PATH_FROM, CORRECT_PATH_TO);
        String actual = Files.readString(Path.of(CORRECT_PATH_TO));
        assertNotEquals(expected,actual);
    }

    @Test
    public void getReport_pathFromIsEmpty_NotOk() {
        try {
            reportService.getReport(EMPTY_PATH, CORRECT_PATH_TO);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'The writing path is not correct' + path");
    }

    @Test
    public void getReport_pathToIsEmpty_NotOk() {
        try {
            reportService.getReport(CORRECT_PATH_FROM, EMPTY_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'The path to the database is not correct' + path");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.records.clear();
    }
}
