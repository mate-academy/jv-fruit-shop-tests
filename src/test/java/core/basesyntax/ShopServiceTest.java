package core.basesyntax;

import core.basesyntax.model.BalanceOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import core.basesyntax.model.PurchaseOperation;
import core.basesyntax.model.ReturnOperation;
import core.basesyntax.model.SupplyOperation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService shopService;
    private List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    public void setUp() {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 35));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 12));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 24));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 11));
    }

    @Test
    void process_normal_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 58);
        Map<String, Integer> actual = shopService.process(fruitTransactions);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void process_quantityLessThenZero_throwsException() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", -35));
        Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(fruitTransactions));
    }

    @Test
    void process_emptyTransactionList_notOk() {
        fruitTransactions.clear();
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = shopService.process(fruitTransactions);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void process_nullFruitTitle_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null, 35));
        Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(fruitTransactions));
    }
}
