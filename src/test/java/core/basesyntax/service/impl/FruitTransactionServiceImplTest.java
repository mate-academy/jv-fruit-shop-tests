package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl();

    @Test
    void getFruitTransactionsFromFile_Ok() {
        final List<FruitTransaction> actualList = fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/wrongData.txt");

        final List<FruitTransaction> expectedList = new ArrayList<>();

        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(20);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);

        expectedList.add(fruitTransaction1);
        expectedList.add(fruitTransaction2);

        assertEquals(expectedList, actualList);
    }

    @Test
    void getFruitTransactionsFromFile_WrongData_NotOk() {
        final List<FruitTransaction> actualList = fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/wrongData.txt");

        final List<FruitTransaction> expectedList = new ArrayList<>();

        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("orange");
        fruitTransaction1.setQuantity(20);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);

        expectedList.add(fruitTransaction1);
        expectedList.add(fruitTransaction2);

        assertNotEquals(expectedList, actualList);
    }

    @Test
    void getFruitTransactionsFromFile_QuantityIsLetters_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/quantityIsLetter.txt"));
    }

    @Test
    void getFruitTransactionsFromFile_WrongOperation_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/wrongOperation.txt"));
    }

    @Test
    void getFruitTransactionsFromFile_WrongTransactionDataQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(
                        "src/test/resources/wrongTransactionDataQuantity.txt"));
    }

    @Test
    void getFruitTransactionsFromFile_EmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/emptyFile.txt"));
    }

    @Test
    void getFruitTransactionsFromFile_WithoutTitle_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile("src/test/resources/withoutTitle.txt"));
    }
}
