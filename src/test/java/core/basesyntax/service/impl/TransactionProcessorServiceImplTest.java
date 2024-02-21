package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorServiceImplTest {
    private static TransactionProcessorService transactionService =
            new TransactionProcessorServiceImpl();
    private String[] fruits = {"Apple", "Banana", "Pear", "Pineapple"};
    private Integer[] quantities = {40, 120, 500, 123};
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            transactions.add(new FruitTransaction());
            transactions.get(i).setFruit(fruits[i]);
            transactions.get(i).setQuantity(quantities[i]);
        }
    }

    @Test
    void service_processBalanceTransactions_Ok() {
        for (int i = 0; i < 4; i++) {
            transactions.get(i).setOperation(FruitTransaction.Operation.BALANCE);
        }
        int expectedSize = 4;

        transactionService.processTransactions(transactions);

        assertEquals(expectedSize, Storage.getFruitStorage().size());
        assertEquals(quantities[0], Storage.getFruitStorage().get(fruits[0]));
        assertEquals(quantities[1], Storage.getFruitStorage().get(fruits[1]));
        assertEquals(quantities[2], Storage.getFruitStorage().get(fruits[2]));
        assertEquals(quantities[3], Storage.getFruitStorage().get(fruits[3]));
    }

    @Test
    void service_processPurchaseTransactions_Ok() {
        for (int i = 0; i < 4; i++) {
            transactions.get(i).setOperation(FruitTransaction.Operation.PURCHASE);
            Storage.getFruitStorage().put(fruits[i], (quantities[i] + 500));
        }
        int expectedSize = 4;
        int expectedQuantityOfEach = 500;

        transactionService.processTransactions(transactions);

        assertEquals(expectedSize, Storage.getFruitStorage().size());
        assertEquals(expectedQuantityOfEach, Storage.getFruitStorage().get(fruits[0]));
        assertEquals(expectedQuantityOfEach, Storage.getFruitStorage().get(fruits[1]));
        assertEquals(expectedQuantityOfEach, Storage.getFruitStorage().get(fruits[2]));
        assertEquals(expectedQuantityOfEach, Storage.getFruitStorage().get(fruits[3]));
    }

    @Test
    void service_processNegativePurchaseTransactions_NotOk() {
        for (int i = 0; i < 4; i++) {
            transactions.get(i).setOperation(FruitTransaction.Operation.PURCHASE);
            Storage.getFruitStorage().put(fruits[i], 5);
        }
        int expectedSize = 4;

        assertEquals(expectedSize, Storage.getFruitStorage().size());
        assertThrows(RuntimeException.class,
                () -> transactionService.processTransactions(transactions));
    }

    @Test
    void service_processReturnTransactions_Ok() {
        int baseStock = 500;
        for (int i = 0; i < 4; i++) {
            transactions.get(i).setOperation(FruitTransaction.Operation.RETURN);
            Storage.getFruitStorage().put(fruits[i], baseStock);
        }
        int expectedSize = 4;

        transactionService.processTransactions(transactions);

        assertEquals(expectedSize, Storage.getFruitStorage().size());
        assertEquals(baseStock + quantities[0], Storage.getFruitStorage().get(fruits[0]));
        assertEquals(baseStock + quantities[1], Storage.getFruitStorage().get(fruits[1]));
        assertEquals(baseStock + quantities[2], Storage.getFruitStorage().get(fruits[2]));
        assertEquals(baseStock + quantities[3], Storage.getFruitStorage().get(fruits[3]));
    }

    @Test
    void service_processSupplyTransactions_Ok() {
        int baseStock = 67;
        for (int i = 0; i < 4; i++) {
            transactions.get(i).setOperation(FruitTransaction.Operation.SUPPLY);
            Storage.getFruitStorage().put(fruits[i], baseStock);
        }
        int expectedSize = 4;

        transactionService.processTransactions(transactions);

        assertEquals(expectedSize, Storage.getFruitStorage().size());
        assertEquals(quantities[0] + baseStock, Storage.getFruitStorage().get(fruits[0]));
        assertEquals(quantities[1] + baseStock, Storage.getFruitStorage().get(fruits[1]));
        assertEquals(quantities[2] + baseStock, Storage.getFruitStorage().get(fruits[2]));
        assertEquals(quantities[3] + baseStock, Storage.getFruitStorage().get(fruits[3]));
    }
}
