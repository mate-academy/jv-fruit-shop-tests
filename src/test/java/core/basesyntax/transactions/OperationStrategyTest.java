package core.basesyntax.transactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.transactions.impl.BalanceOperationHandler;
import core.basesyntax.transactions.impl.PurchaseOperationHandler;
import core.basesyntax.transactions.impl.ReturnOperationHandler;
import core.basesyntax.transactions.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 100;
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> transactionStrategies;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        transactionStrategies = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler()
        );
        operationStrategy = new OperationStrategy(transactionStrategies);
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY);
    }

    @Test
    void handler_withBalanceOperation_OK() {
        OperationHandler actualHandler = operationStrategy.handler(fruitTransaction);
        OperationHandler expected = transactionStrategies.get(Operation.BALANCE);

        assertEquals(expected, actualHandler);
    }

    @Test
    void handler_withNullOperation_NotOK() {
        fruitTransaction.setOperation(null);
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.handler(fruitTransaction));
    }

    @Test
    void handler_withNullFruit_NotOK() {
        fruitTransaction.setFruit(null);
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.handler(fruitTransaction));
    }

    @Test
    void handler_negativeQuantity_NotOK() {
        fruitTransaction.setQuantity(-1);
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.handler(fruitTransaction));
    }
}
