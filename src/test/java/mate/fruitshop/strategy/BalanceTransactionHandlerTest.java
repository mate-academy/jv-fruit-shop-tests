package mate.fruitshop.strategy;

import static mate.fruitshop.TransactionHandlerTest.DEFAULT_FRUIT;
import static mate.fruitshop.TransactionHandlerTest.DEFAULT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitBalanceHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceTransactionHandlerTest {
    private static FruitTransactionHandler balanceHandler;
    private static FruitTransaction balanceTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new FruitBalanceHandler();
        balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    void conductTransaction_balanceTransaction_ok() {
        assertEquals(DEFAULT_QUANTITY, balanceHandler.conductTransaction(balanceTransaction, 0));
    }
}
