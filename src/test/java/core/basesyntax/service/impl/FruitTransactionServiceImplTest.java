package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitShopDao fruitShopDao;
    private static FruitStrategy fruitStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static FruitTransactionService fruitTransactionService;
    private static List<FruitTransaction> inputList;

    @BeforeAll
    static void beforeAll() {
        fruitShopDao = new FruitShopDaoCsvImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler(fruitShopDao));
        fruitStrategy = new FruitStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(fruitShopDao, fruitStrategy);
        inputList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitQuantities.clear();
    }

    @Test
    void processTransactions_validInputList_ok() {
        inputList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        inputList.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        String expected = "[banana=20, apple=100]";
        String actual = fruitTransactionService.processTransactions(inputList);
        assertEquals(expected, actual);
    }
}
