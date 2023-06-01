package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorImplTest {
    private static final String BANANA_FRUIT = "banana";
    private OperationStrategy operationStrategy;
    private DataProcessor dataProcessor;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                BALANCE, new BalanceOperationHandler(),
                RETURN, new ReturnOperationHandler(),
                PURCHASE, new PurchaseOperationHandler(),
                SUPPLY, new SupplyOperationHandler()
        ));
        dataProcessor = new DataProcessorImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @Test
    void addDataToDB_validData_ok() {
        fruitTransactions = List.of(
                new FruitTransaction(BALANCE, BANANA_FRUIT,100),
                new FruitTransaction(PURCHASE, BANANA_FRUIT,90),
                new FruitTransaction(SUPPLY, BANANA_FRUIT,100),
                new FruitTransaction(RETURN, BANANA_FRUIT,50)
        );
        dataProcessor.addDataToDB(fruitTransactions);
        int expacted = 160;
        int actual = Storage.FRUIT_STORAGE.get(BANANA_FRUIT);
        Assertions.assertEquals(expacted, actual);
    }

    @Test
    void addDataToDB_invalidData_notOk() {
        fruitTransactions = List.of(
                new FruitTransaction(BALANCE, BANANA_FRUIT,100),
                new FruitTransaction(PURCHASE, BANANA_FRUIT,120)
        );
        Assertions.assertThrows(RuntimeException.class,
                () -> dataProcessor.addDataToDB(fruitTransactions));
    }
}
