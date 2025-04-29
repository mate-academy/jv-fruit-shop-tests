package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {

    private ShopServiceImpl shopServiceImpl;

    private Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    private OperationStrategy operationStrategy;

    private Map<String, Integer> expectedMap = new HashMap<>();

    private List<FruitTransaction> testFruitTransactionList = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );

    @BeforeEach
    void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(handlerMap);
        shopServiceImpl = new ShopServiceImpl(operationStrategy);

        expectedMap = new HashMap<>();
        expectedMap.put("banana", 152); //expected this amount of bananas after process
        expectedMap.put("apple", 90); // expected this amount of apples after process
    }

    @Test
    void process_validInput_ok() {
        Storage.fruitStorage.clear();

        shopServiceImpl.process(testFruitTransactionList);

        assertEquals(expectedMap, Storage.fruitStorage);
    }
}
