package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.entity.Operation;
import core.basesyntax.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static final String PATH_TO_DAILY_ACTIVITY_FILE
            = "src/test/resources/testdailtactivity.csv";
    private static final String PATH_TO_REPORT_FILE
            = "src/test/resources/testreport.csv";
    private static final String REPORT_COLUMN_NAMES = "type,fruit,quantity";
    private static final String FRUIT_TRANSACTION_BANANA = "s,banana,25";
    private static final String FRUIT_TRANSACTION_APPLE = "p,apple,88";
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final int QUANTITY_OF_BANANA = 25;
    private static final int QUANTITY_OF_APPLE = 88;
    private TransactionService transactionService;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoCsvImpl(
                PATH_TO_DAILY_ACTIVITY_FILE, PATH_TO_REPORT_FILE);
        transactionService = new TransactionServiceImpl(fruitTransactionDao);
        transaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        TestHelper.clearShopActivityFile(PATH_TO_DAILY_ACTIVITY_FILE);
    }

    @Test
    void writeTransactionToFile_success_Ok() {
        transaction.setOperation(Operation.SUPPLY);
        transaction.setFruit(BANANA_FRUIT);
        transaction.setQuantity(QUANTITY_OF_BANANA);
        transactionService.writeTransactionToFile(transaction);
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit(APPLE_FRUIT);
        transaction.setQuantity(QUANTITY_OF_APPLE);
        transactionService.writeTransactionToFile(transaction);
        String expected = REPORT_COLUMN_NAMES + System.lineSeparator()
                + FRUIT_TRANSACTION_BANANA + System.lineSeparator()
                + FRUIT_TRANSACTION_APPLE;
        String actual = TestHelper.getActualStringFromCsv(PATH_TO_DAILY_ACTIVITY_FILE);
        assertEquals(expected,actual);
    }

    @Test
    void writeTransactionToFile_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.writeTransactionToFile(null));
    }
}
