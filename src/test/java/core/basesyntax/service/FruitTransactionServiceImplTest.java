package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {

    private static final String FRUIT_APPLE = "Apple";
    private static final String FRUIT_BANANA = "Banana";
    private static final String FRUIT_ORANGE = "Orange";
    private static final String FRUIT_GRAPES = "Grapes";
    private static final String FRUIT_PINEAPPLE = "Pineapple";
    private static final String FRUIT_STRAWBERRY = "Strawberry";

    private static final int FIFTY_QUANTITY = 50;
    private static final int TWENTY_QUANTITY = 20;
    private static final int TWENTY_FIVE_QUANTITY = 25;
    private static final int TEN_QUANTITY = 10;

    private FruitTransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new FruitTransactionServiceImpl();
    }

    @Test
    void handle_processBalanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, FRUIT_APPLE,
                FIFTY_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY, storage.get(FRUIT_APPLE));
    }

    @Test
    void handle_processSupplyOperation_ok() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, FRUIT_BANANA,
                FIFTY_QUANTITY));
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, FRUIT_BANANA,
                TWENTY_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY + TWENTY_QUANTITY, storage.get(FRUIT_BANANA));
    }

    @Test
    void handle_processPurchaseOperation_ok() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, FRUIT_ORANGE,
                FIFTY_QUANTITY + TEN_QUANTITY));
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, FRUIT_ORANGE,
                TWENTY_FIVE_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY + TEN_QUANTITY - TWENTY_FIVE_QUANTITY,
                storage.get(FRUIT_ORANGE));
    }

    @Test
    void handle_throwErrorForInsufficientQuantityDuringPurchase_notOk() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, FRUIT_GRAPES,
                FIFTY_QUANTITY));
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                FRUIT_GRAPES, FIFTY_QUANTITY + TEN_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY, storage.get(FRUIT_GRAPES));
    }

    @Test
    void handle_processReturnOperation_ok() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, FRUIT_PINEAPPLE,
                FIFTY_QUANTITY));
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, FRUIT_PINEAPPLE,
                TEN_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY + TEN_QUANTITY, storage.get(FRUIT_PINEAPPLE));
    }

    @Test
    void handle_addNewFruitWithBalanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE,
                FRUIT_STRAWBERRY, FIFTY_QUANTITY);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(FIFTY_QUANTITY, storage.get(FRUIT_STRAWBERRY));
    }

    @Test
    void handle_ignoreUnsupportedOperation_notOk() {
        FruitTransaction transaction = new FruitTransaction(null, FRUIT_APPLE,
                FIFTY_QUANTITY);
        assertThrows(NullPointerException.class,
                () -> transactionService.handle(transaction));
    }
}
