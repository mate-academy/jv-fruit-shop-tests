package mate.fruitshop;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitBalanceHandler;
import mate.fruitshop.service.strategy.FruitPurchaseHandler;
import mate.fruitshop.service.strategy.FruitReturnHandler;
import mate.fruitshop.service.strategy.FruitSupplyHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionHandlerTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    public static final int DEFAULT_QUANTITY = 100;

    private static List<FruitTransactionHandler> transactionHandlers;

    @BeforeAll
    static void init() {
        transactionHandlers = List.of(new FruitBalanceHandler(),
                new FruitPurchaseHandler(), new FruitReturnHandler(), new FruitSupplyHandler());
    }

    @Test
    void conductTransaction_negativeQuantityValue_notOk() {
        FruitTransaction negativeQuantityTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT, -1);
        for (FruitTransactionHandler handler : transactionHandlers) {
            assertThrows(IllegalArgumentException.class,
                    () -> handler.conductTransaction(negativeQuantityTransaction, 0));
        }
    }

    @Test
    void conductTransaction_zeroQuantityValue_ok() {
        FruitTransaction zeroQuantityTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT, 0);
        for (FruitTransactionHandler handler : transactionHandlers) {
            assertDoesNotThrow(() -> handler.conductTransaction(zeroQuantityTransaction, 0),
                    "Exception thrown for zero transaction quantity");
        }
    }
}
