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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final String PATH1 = "./src/test/resources/testData";
    private static final String PATH2 = "./src/test/resources/testData2";
    private static final Map<Operation, OperationHandler> handlerMap = new HashMap<>();
    private final FileService fileService = new FileServiceImpl();
    private final ReportService reportService = new ReportServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final FruitshopService fruitshopService = new FruitshopServiceImpl(handlerMap);

    @Test
    void checkResultOne_Ok() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(PATH1);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,10" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        Assertions.assertEquals(expected, report);
    }

    @Test
    void checkResultTwo_Ok() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(PATH2);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,15" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        Assertions.assertEquals(expected, report);
    }

    private static void initializeMap() {
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }
}
