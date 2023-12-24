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
        fruitTransaction = new FruitTransaction(BALANCE, "apple", 23);
        assertEquals(new BalanceHandler().calculate(fruitTransaction),
                operationStrategy.get(BALANCE).calculate(fruitTransaction));
    }

    @Test
    void get_purchaseHandler_ok() {
        fruitTransaction = new FruitTransaction(PURCHASE, "lime", 12);
        assertEquals(new PurchaseHandler().calculate(fruitTransaction),
                operationStrategy.get(PURCHASE).calculate(fruitTransaction));
    }

    @Test
    void get_returnHandler_ok() {
        fruitTransaction = new FruitTransaction(RETURN, "lemon", 10);
        assertEquals(new ReturnHandler().calculate(fruitTransaction),
                operationStrategy.get(RETURN).calculate(fruitTransaction));
    }

    @Test
    void get_supplyHandler_ok() {
        fruitTransaction = new FruitTransaction(SUPPLY, "coconut", 7);
        assertEquals(new SupplyHandler().calculate(fruitTransaction),
                operationStrategy.get(SUPPLY).calculate(fruitTransaction));
    }
}
