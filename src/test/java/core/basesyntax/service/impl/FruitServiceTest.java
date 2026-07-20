package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private FruitService fruitService;
    private List<FruitTransaction> transactions;
    private FruitTransaction firstTransaction;
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategies;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 0);

        operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        fruitService = new FruitServiceImpl(operationStrategies);

        transactions = new ArrayList<>();
        firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("apple");
        firstTransaction.setOperation(FruitTransaction.Operation.RETURN);
        firstTransaction.setQuantity(2);
        transactions.add(firstTransaction);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_returnOperation_ok() {
        Map<String, Integer> result = fruitService.process(transactions);
        assertEquals(2, result.get("apple").intValue());
    }

    @Test
    void process_noHandlerForOperation_notOk() {
        firstTransaction.setOperation(FruitTransaction.Operation.SUPPLY);

        assertThrows(RuntimeException.class,
                () -> fruitService.process(transactions));
    }

    @Test
    void process_purchaseMoreThanAvailable_notOk() {
        operationStrategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        firstTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        firstTransaction.setQuantity(1000); // більше, ніж є в Storage

        assertThrows(RuntimeException.class,
                () -> fruitService.process(transactions));
    }
}
