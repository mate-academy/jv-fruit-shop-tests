package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.Balance;
import core.basesyntax.strategy.impl.Purchase;
import core.basesyntax.strategy.impl.Return;
import core.basesyntax.strategy.impl.Supply;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private TransactionProcessorImpl processor;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        FruitDao fruitDao = new FruitDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategy(Map.of(
                Operation.BALANCE, new Balance(),
                Operation.SUPPLY, new Supply(),
                Operation.PURCHASE, new Purchase(),
                Operation.RETURN, new Return()
        ));
        processor = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    void process_ValidTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 50),
                new FruitTransaction(Operation.PURCHASE, "banana", 30),
                new FruitTransaction(Operation.RETURN, "banana", 10)
        );

        processor.process(transactions);
        assertEquals(130, Storage.fruits.get("banana"));
    }

    @Test
    void process_PurchaseMoreThanBalance_ThrowsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 30)
        );

        assertThrows(RuntimeException.class, () -> processor.process(transactions));
    }
}
