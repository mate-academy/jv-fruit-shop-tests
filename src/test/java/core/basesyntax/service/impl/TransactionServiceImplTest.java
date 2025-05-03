package core.basesyntax.service.impl;

import static core.basesyntax.db.FruitStorage.fruitInventory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionServiceImplTest {
    private static TransactionService transactionService;
    private static final int EXPECTED_BANANA_QUANTITY = 25;
    private static final int EXPECTED_ORANGE_QUANTITY = 50;

    @BeforeAll
    static void beforeAll() {
        transactionService = new TransactionServiceImpl();
        fruitInventory.clear();
    }

    @Test
    void calculate_Transactions_Ok() {
        List<FruitTransaction> inputData = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 95)
        );
        transactionService.executeTransactions(inputData);
        int actualBananaQuantity = fruitInventory.get("banana");
        int actualOrangeQuantity = fruitInventory.get("orange");
        assertEquals(EXPECTED_BANANA_QUANTITY, actualBananaQuantity);
        assertEquals(EXPECTED_ORANGE_QUANTITY, actualOrangeQuantity);
    }

    @Test
    void calculate_NullTransactions_NotOk() {
        assertThrows(NullPointerException.class, () ->
                transactionService.executeTransactions(null));
    }

    @Test
    void calculate_WrongTransaction_NotOk() {
        List<FruitTransaction> inputData = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50)
        );
        assertThrows(RuntimeException.class, () ->
                transactionService.executeTransactions(inputData));
    }
}
