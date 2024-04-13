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

public class ReportServiceTest {
    private static final String DATA_FILE_PATH = "./src/main/resources/beginningData";
    private static final Map<Operation, OperationHandler> handlerMap = new HashMap<>();
    private final FileService fileService = new FileServiceImpl();
    private final ReportService reportService = new ReportServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final FruitshopService fruitshopService = new FruitshopServiceImpl(handlerMap);

    @Test
    void generateReport_Ok() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String actual = reportService.createReport();
        String expected = "banana,90" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
        Assertions.assertEquals(expected, actual);
    }

    private static void initializeMap() {
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }
}
