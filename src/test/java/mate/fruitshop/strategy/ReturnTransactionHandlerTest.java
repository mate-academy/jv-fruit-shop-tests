package mate.fruitshop.strategy;

import static mate.fruitshop.TransactionHandlerTest.DEFAULT_FRUIT;
import static mate.fruitshop.TransactionHandlerTest.DEFAULT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitReturnHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnTransactionHandlerTest {
    private static FruitTransactionHandler returnHandler;
    private static FruitTransaction returnTransaction;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new FruitReturnHandler();
        returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    void conductTransaction_returnTransaction_ok() {
        assertEquals(DEFAULT_QUANTITY + DEFAULT_QUANTITY,
                returnHandler.conductTransaction(returnTransaction, DEFAULT_QUANTITY));
    }
}
