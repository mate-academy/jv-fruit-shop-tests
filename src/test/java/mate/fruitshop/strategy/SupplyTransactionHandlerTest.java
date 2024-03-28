package mate.fruitshop.strategy;

import static mate.fruitshop.TransactionHandlerTest.DEFAULT_FRUIT;
import static mate.fruitshop.TransactionHandlerTest.DEFAULT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitSupplyHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyTransactionHandlerTest {
    private static FruitTransactionHandler supplyHandler;
    private static FruitTransaction supplyTransaction;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new FruitSupplyHandler();
        supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    void conductTransaction_supplyTransaction_ok() {
        assertEquals(DEFAULT_QUANTITY + 1, supplyHandler.conductTransaction(supplyTransaction, 1));
    }
}
