package mate.fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitBalanceHandler;
import mate.fruitshop.service.strategy.FruitPurchaseHandler;
import mate.fruitshop.service.strategy.FruitReturnHandler;
import mate.fruitshop.service.strategy.FruitSupplyHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.Test;

public class TransactionHandlerStrategyTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    public static final int DEFAULT_QUANTITY = 100;

    @Test
    void conductTransaction_negativeQuantityValue_notOk() {
        FruitTransaction negativeQuantityTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT, -1);

        List<FruitTransactionHandler> transferHandlers = List.of(new FruitBalanceHandler(),
                new FruitPurchaseHandler(), new FruitReturnHandler(), new FruitSupplyHandler());
        for (FruitTransactionHandler handler : transferHandlers) {
            assertThrows(IllegalArgumentException.class,
                    () -> handler.conductTransaction(negativeQuantityTransaction, 0));
        }
    }

    @Test
    void conductTransaction_balanceTransaction_ok() {
        FruitTransactionHandler balanceHandler = new FruitBalanceHandler();
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, balanceHandler.conductTransaction(balanceTransaction, 0));
    }

    @Test
    void conductTransaction_supplyTransaction_ok() {
        FruitTransactionHandler supplyHandler = new FruitSupplyHandler();
        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY + 1, supplyHandler.conductTransaction(supplyTransaction, 1));
    }

    @Test
    void conductTransaction_purchaseTransaction_ok() {
        FruitTransactionHandler purchaseHandler = new FruitPurchaseHandler();
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(0, purchaseHandler.conductTransaction(purchaseTransaction, DEFAULT_QUANTITY));
    }

    @Test
    void conductTransaction_purchaseTransactionNotEnoughFruits_notOk() {
        FruitTransactionHandler purchaseHandler = new FruitPurchaseHandler();
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertThrows(IllegalArgumentException.class, () ->
                purchaseHandler.conductTransaction(purchaseTransaction, DEFAULT_QUANTITY - 1));
    }

    @Test
    void conductTransaction_returnTransaction_ok() {
        FruitTransactionHandler returnHandler = new FruitReturnHandler();
        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY + DEFAULT_QUANTITY,
                returnHandler.conductTransaction(returnTransaction, DEFAULT_QUANTITY));
    }
}
