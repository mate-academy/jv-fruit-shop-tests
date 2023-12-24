package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String APPLE = "apple";
    private static final String LIME = "lime";
    private static final String LEMON = "lemon";
    private static final String COCONUT = "coconut";
    private static Map<FruitTransaction.Operation, OperationHandler> operationMap;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationMap = Map.of(BALANCE, new BalanceHandler(),
                              PURCHASE, new PurchaseHandler(),
                              RETURN, new ReturnHandler(),
                              SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(operationMap);
    }

    @Test
    void get_balanceHandler_ok() {
        fruitTransaction = new FruitTransaction(BALANCE, APPLE, 23);
        var expected = new BalanceHandler().calculate(fruitTransaction);
        var actual = operationStrategy.get(BALANCE).calculate(fruitTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void get_purchaseHandler_ok() {
        fruitTransaction = new FruitTransaction(PURCHASE, LIME, 12);
        var expected = new PurchaseHandler().calculate(fruitTransaction);
        var actual = operationStrategy.get(PURCHASE).calculate(fruitTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void get_returnHandler_ok() {
        fruitTransaction = new FruitTransaction(RETURN, LEMON, 10);
        var expected = new ReturnHandler().calculate(fruitTransaction);
        var actual = operationStrategy.get(RETURN).calculate(fruitTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void get_supplyHandler_ok() {
        fruitTransaction = new FruitTransaction(SUPPLY, COCONUT, 7);
        var expected = new SupplyHandler().calculate(fruitTransaction);
        var actual = operationStrategy.get(SUPPLY).calculate(fruitTransaction);
        assertEquals(expected, actual);
    }
}
