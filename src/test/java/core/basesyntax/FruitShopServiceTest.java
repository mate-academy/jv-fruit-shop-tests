package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopServiceTest {
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    void applyTransactions_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);

        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append("fruit,quantity").append(System.lineSeparator());
        expectedReport.append("apple,20").append(System.lineSeparator());;
        assertEquals(expectedReport.toString(), fruitShopService.applyTransactions(transactions));
    }

    @Test
    void applyTransactions_quantityLessThanZero_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);
        assertThrows(RuntimeException.class,() ->
                fruitShopService.applyTransactions(transactions));
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
