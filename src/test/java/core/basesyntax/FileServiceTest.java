package core.basesyntax;

import core.basesyntax.database.Operation;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.impl.FileServiceImpl;
import core.basesyntax.impl.FruitshopServiceImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.impl.TransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FruitshopService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileServiceTest {

    private static final Map<Operation, OperationHandler> handlerMap = new HashMap<>();
    private static final String PATH3 = "./src/test/resources/testData3";
    private static final String DATA_FILE_PATH = "./src/main/resources/beginningData";
    private static final String REPORT_FILE_PATH = "./src/main/resources/report";
    private static final String INVALID_PATH = "./src/main/user/data/content/file";
    private static final FileService fileService = new FileServiceImpl();
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final FruitshopService fruitshopService = new FruitshopServiceImpl(handlerMap);
    private static final ReportService reportService = new ReportServiceImpl();

    @Test
    void parseData_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,100");
        expected.add("b,apple,100");
        List<String> actual = fileService.readDataFromFile(PATH3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeToFile_Ok() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,90" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
        fileService.write(REPORT_FILE_PATH, report);
        List<String> checkReport = fileService.readDataFromFile(REPORT_FILE_PATH);
        StringBuilder builder = new StringBuilder();
        for (String value : checkReport) {
            builder.append(value);
            builder.append(System.lineSeparator());
        }
        Assertions.assertEquals(expected, builder.toString());
    }

    @Test
    void fileServiceThrows_Ok() {
        String content = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.readDataFromFile(INVALID_PATH));
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.write(INVALID_PATH, content));
    }

    private static void initializeMap() {
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }
}
