package core.basesyntax.service.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static core.basesyntax.util.FruitTestConstants.BANANA;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransactionProcessorImpl;
import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private static FruitTransactionProcessor processor;
    private static Map<FruitTransaction.Operation, OperationsHandler> handlersMap = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        handlersMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitDao),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(fruitDao)
        );

        OperationStrategy operationStrategy = new OperationStrategy(handlersMap);
        processor = new FruitTransactionProcessorImpl(operationStrategy);
    }

    @Test
    void processTransactions_ValidTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 30)
        );
        assertDoesNotThrow(() -> processor.processTransactions(transactions));
    }

    @Test
    void processTransactions_EmptyList_Ok() {
        List<FruitTransaction> transactions = List.of();
        assertDoesNotThrow(() ->
                processor.processTransactions(transactions),
                "Processing an empty list should not throw an exception.");
    }

    @Test
    void processTransactions_NullList_NotOk() {
        assertThrows(NullPointerException.class, () -> processor.processTransactions(null),
                "Processing a null list of transactions should throw NullPointerException.");
    }
}
