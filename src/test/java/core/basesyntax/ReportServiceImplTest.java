package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.CsvFileService;
import core.basesyntax.dao.CsvFileServiceImpl;
import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.ActivityStrategyImpl;
import core.basesyntax.service.InputValidator;
import core.basesyntax.service.InputValidatorImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceActivityHandler;
import core.basesyntax.service.activityhandler.PurchaseActivityHandler;
import core.basesyntax.service.activityhandler.ReturnActivityHandler;
import core.basesyntax.service.activityhandler.SupplyActivityHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String OK_READ_FILE_PATH = "src/test/resources/shopInputTest_OK.csv";
    private static final String OK_WRITE_FILE_PATH = "src/test/resources/shopOutput.csv";
    private static final String EXPECTED_OK_FILE_PATH = "src/test/resources/expectedShopOutput.csv";

    private static final Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
    private static final CsvFileService readerDao = new CsvFileServiceImpl();
    private static final InputValidator inputValidator = new InputValidatorImpl();
    private static final ActivityStrategy strategy = new ActivityStrategyImpl(activityHandlerMap);
    private static final ActivityHandler balanceActivityHandler = new BalanceActivityHandler();
    private static final ActivityHandler purchaseActivityHandler = new PurchaseActivityHandler();
    private static final ActivityHandler returnActivityHandler = new ReturnActivityHandler();
    private static final ActivityHandler supplyActivityHandler = new SupplyActivityHandler();

    @Before
    public void setUp() {
        activityHandlerMap.put("b", balanceActivityHandler);
        activityHandlerMap.put("p", purchaseActivityHandler);
        activityHandlerMap.put("r", returnActivityHandler);
        activityHandlerMap.put("s", supplyActivityHandler);
    }

    @Test
    public void generateReport_Ok() {
        ReportService report = new ReportServiceImpl(readerDao, inputValidator, strategy);
        report.generateReport(OK_READ_FILE_PATH, OK_WRITE_FILE_PATH);
        try {
            List<String> actual = Files.readAllLines(Path.of(OK_WRITE_FILE_PATH));
            List<String> expected = Files.readAllLines(Path.of(EXPECTED_OK_FILE_PATH));
            assertEquals(actual,expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't read files to compare" + e);
        }
    }
}
