package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operators.BalanceOperationHandler;
import core.basesyntax.operators.OperationHandler;
import core.basesyntax.operators.PurchaseOperationHandler;
import core.basesyntax.operators.ReturnOperationHandler;
import core.basesyntax.operators.SupplyOperationHandler;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.OperatorStrategyImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationToHandlerMap
            = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private FruitTransactionService fruitTransactionService;

    @BeforeEach
    void setUp() {
        fruitTransactionService
                = new FruitTransactionServiceImpl(new OperatorStrategyImpl(operationToHandlerMap));
    }

    @Test
    public void testProcessValidTransactions() {
        List<FruitTransaction> sampleTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)
        );
        fruitTransactionService.process(sampleTransactions);
        assertEquals(50, (int) Storage.storage.getOrDefault("banana", 0),
                "Balance should be 30 for 'banana'");
        Storage.storage.clear();
    }
}
