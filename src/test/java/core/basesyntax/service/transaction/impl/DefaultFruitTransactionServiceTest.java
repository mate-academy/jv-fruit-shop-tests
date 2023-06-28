package core.basesyntax.service.transaction.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.LocalStorageFruitDao;
import core.basesyntax.db.LocalStorage;
import core.basesyntax.exception.InvalidFruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.FruitTransactionService;
import core.basesyntax.service.transaction.FruitTransactionValidator;
import core.basesyntax.strategy.FruitTransactionOperationHandler;
import core.basesyntax.strategy.FruitTransactionOperationStrategy;
import core.basesyntax.strategy.impl.BalanceFruitTransactionOperationHandler;
import core.basesyntax.strategy.impl.DefaultFruitTransactionOperationStrategy;
import core.basesyntax.strategy.impl.PurchaseFruitTransactionOperationHandler;
import core.basesyntax.strategy.impl.ReturnFruitTransactionOperationHandler;
import core.basesyntax.strategy.impl.SupplyFruitTransactionOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultFruitTransactionServiceTest {
    private static final List<FruitTransaction> VALID_TRANSACTIONS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );
    private static final List<FruitTransaction> INVALID_TRANSACTION = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -2)
    );
    private static final List<FruitTransaction> INVALID_IN_THE_MIDDLE = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );

    private static FruitTransactionService fruitTransactionService;

    @BeforeAll
    static void beforeAll() {
        FruitTransactionValidator validator = new DefaultFruitTransactionValidator();
        FruitDao fruitDao = new LocalStorageFruitDao();
        Map<FruitTransaction.Operation, FruitTransactionOperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE,
                new BalanceFruitTransactionOperationHandler(fruitDao),

                FruitTransaction.Operation.SUPPLY,
                new SupplyFruitTransactionOperationHandler(fruitDao),

                FruitTransaction.Operation.PURCHASE,
                new PurchaseFruitTransactionOperationHandler(fruitDao),

                FruitTransaction.Operation.RETURN,
                new ReturnFruitTransactionOperationHandler(fruitDao)
        );
        FruitTransactionOperationStrategy strategy =
                new DefaultFruitTransactionOperationStrategy(handlers);
        fruitTransactionService = new DefaultFruitTransactionService(validator, strategy);
        LocalStorage.FRUITS.clear();
    }

    @AfterEach
    void tearDown() {
        LocalStorage.FRUITS.clear();
    }

    @Test
    void processTransactions_validTransactions_ok() {
        fruitTransactionService.processTransactions(VALID_TRANSACTIONS);
        assertTrue(() -> LocalStorage.FRUITS.size() == 2
                && LocalStorage.FRUITS.get("apple").equals(90)
                && LocalStorage.FRUITS.get("banana").equals(152));
    }

    @Test
    void processTransactions_invalidTransaction_notOk() {
        assertThrows(
                InvalidFruitTransactionException.class,
                () -> fruitTransactionService.processTransactions(INVALID_TRANSACTION)
        );
    }

    @Test
    void processTransactions_invalidInTheMiddle_notOk() {
        assertThrows(
                InvalidFruitTransactionException.class,
                () -> fruitTransactionService.processTransactions(INVALID_IN_THE_MIDDLE)
        );
        assertTrue(() -> LocalStorage.FRUITS.size() == 2
                && LocalStorage.FRUITS.get("apple").equals(100)
                && LocalStorage.FRUITS.get("banana").equals(120));
    }
}
