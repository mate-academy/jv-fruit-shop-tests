package core.basesyntax;

import core.basesyntax.database.DataBase;
import core.basesyntax.database.Operation;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.impl.FileServiceImpl;
import core.basesyntax.impl.FruitshopServiceImpl;
import core.basesyntax.impl.TransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FruitshopService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitshopServiceTest {
    private static final Map<Operation, OperationHandler> handlerMap = new HashMap<>();
    private static final String PATH4 = "./src/test/resources/testData4";
    private static final String DATA_FILE_PATH = "./src/main/resources/beginningData";
    private static final FileService fileService = new FileServiceImpl();
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final FruitshopService fruitshopService = new FruitshopServiceImpl(handlerMap);

    @Test
    void processData_Ok() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        Assertions.assertEquals(DataBase.mapDb.get("banana"), 90);
        Assertions.assertEquals(DataBase.mapDb.get("apple"), 150);
    }

    @Test
    void processDataThrows_Ok() {
        List<FruitTransaction> nullFruits = new ArrayList<>();
        nullFruits.add(new FruitTransaction(null, "banana", 100));
        nullFruits.add(new FruitTransaction(null, "apple", 100));
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitshopService.processData(nullFruits));
    }

    @Test
    void fruitShopServiceInvalidDataThrows_OK() {
        List<String> strings = fileService.readDataFromFile(PATH4);
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionService.parseData(strings));
    }

    private static void initializeMap() {
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }
}
