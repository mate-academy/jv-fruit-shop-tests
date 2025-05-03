package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    public static final String FILE_PATH = "src/test/resources/data1.txt";
    public static final String QUANTITY_IS_LETTER_FILE_PATH =
            "src/test/resources/quantityIsLetter.txt";
    public static final String WRONG_OPERATOR_FILE_PATH =
            "src/test/resources/wrongOperation.txt";
    public static final String WRONG_TRANSACTION_DATA_QUANTITY_FILE_PATH =
            "src/test/resources/wrongTransactionDataQuantity.txt";
    public static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.txt";
    public static final String WITHOUT_TITLE_FILE_PATH =
            "src/test/resources/withoutTitle.txt";
    private final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl();

    @Test
    void getFruitTransactionsFromFile_Ok() {
        final List<FruitTransaction> actualList = fruitTransactionService
                .getFruitTransactionsFromFile(FILE_PATH);

        final List<FruitTransaction> expectedList = new ArrayList<>();

        FruitTransaction bananaTransactionBalance = new FruitTransaction();
        bananaTransactionBalance.setOperation(FruitTransaction.Operation.BALANCE);
        bananaTransactionBalance.setFruit("banana");
        bananaTransactionBalance.setQuantity(20);

        FruitTransaction appleTransactionBalance = new FruitTransaction();
        appleTransactionBalance.setOperation(FruitTransaction.Operation.BALANCE);
        appleTransactionBalance.setFruit("apple");
        appleTransactionBalance.setQuantity(100);

        FruitTransaction bananaTransactionSupply = new FruitTransaction();
        bananaTransactionSupply.setOperation(FruitTransaction.Operation.SUPPLY);
        bananaTransactionSupply.setFruit("banana");
        bananaTransactionSupply.setQuantity(100);

        expectedList.add(bananaTransactionBalance);
        expectedList.add(appleTransactionBalance);
        expectedList.add(bananaTransactionSupply);

        assertEquals(expectedList, actualList);
    }

    @Test
    void getFruitTransactionsFromFile_QuantityIsLetters_throwsException() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(QUANTITY_IS_LETTER_FILE_PATH));
    }

    @Test
    void getFruitTransactionsFromFile_WrongOperation_throwsException() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(WRONG_OPERATOR_FILE_PATH));
    }

    @Test
    void getFruitTransactionsFromFile_WrongTransactionDataQuantity_throwsException() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(WRONG_TRANSACTION_DATA_QUANTITY_FILE_PATH));
    }

    @Test
    void getFruitTransactionsFromFile_EmptyFile_throwsException() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(EMPTY_FILE_PATH));
    }

    @Test
    void getFruitTransactionsFromFile_WithoutTitle_throwsException() {
        assertThrows(RuntimeException.class, () -> fruitTransactionService
                .getFruitTransactionsFromFile(WITHOUT_TITLE_FILE_PATH));
    }
}
