package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationStrategyImplTest {
    private OperationStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    void chooseHandlerBalance_OK() {
        Operation operation = Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, "banana", 100);
        assertEquals(BalanceOperation.class, strategy.chooseHandler(transaction).getClass());
    }

    @Test
    void chooseHandlerReturn_OK() {
        Operation operation = Operation.RETURN;
        FruitTransaction transaction = new FruitTransaction(operation, "banana", 100);
        assertEquals(ReturnOperation.class, strategy.chooseHandler(transaction).getClass());
    }

    @Test
    void chooseHandlerPurchase_OK() {
        Operation operation = Operation.PURCHASE;
        FruitTransaction transaction = new FruitTransaction(operation, "banana", 100);
        assertEquals(PurchaseOperation.class, strategy.chooseHandler(transaction).getClass());
    }

    @Test
    void chooseHandlerSupply_OK() {
        Operation operation = Operation.SUPPLY;
        FruitTransaction transaction = new FruitTransaction(operation, "banana", 100);
        assertEquals(SupplyOperation.class, strategy.chooseHandler(transaction).getClass());
    }
}