package core.basesyntax.service.impl;

import core.basesyntax.db.storage.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    public static final List<FruitTransaction> INPUT_TRANSACTION = new ArrayList<>(){};
    public static final List<FruitTransaction> WRONG_INPUT_TRANSACTION = new ArrayList<>(){};
    public static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlers = new HashMap<>();
    public static final Map<String,Integer> EXCEPTED_RESULT = new TreeMap<>();

    @BeforeEach
    void setUp() {
        INPUT_TRANSACTION.add(new FruitTransaction("b", "banana", 20));
        INPUT_TRANSACTION.add(new FruitTransaction("b", "apple", 100));
        INPUT_TRANSACTION.add(new FruitTransaction("s", "banana", 100));
        INPUT_TRANSACTION.add(new FruitTransaction("p", "banana", 115));
        INPUT_TRANSACTION.add(new FruitTransaction("r", "apple", 10));
        INPUT_TRANSACTION.add(new FruitTransaction("p", "apple", 20));
        INPUT_TRANSACTION.add(new FruitTransaction("p", "banana", 5));
        INPUT_TRANSACTION.add(new FruitTransaction("s", "banana", 50));
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        EXCEPTED_RESULT.put("banana",50);
        EXCEPTED_RESULT.put("apple",90);
    }

    @Test
    void processTest_OK() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(INPUT_TRANSACTION);
        Assertions.assertEquals(EXCEPTED_RESULT, Storage.fruits);
    }

    @Test
    void wrongOperationTransaction_OK() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        try {
            WRONG_INPUT_TRANSACTION.add(new FruitTransaction("h", "banana", 50));
            shopService.process(WRONG_INPUT_TRANSACTION);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("you should throw exception: Invalid operation code:  + operation");

    }
}
