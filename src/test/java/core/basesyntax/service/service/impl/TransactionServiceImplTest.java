package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    public static final String COLUMN_NAMES_LINE = "type,fruit,quantity";
    public static final String CORRECT_LINE = "b,apple,200";
    public static final String INCORRECT_FORMAT_CORRECT_DATA = "b-  , apple?,  200";
    public static final String LINE_WITHOUT_QUANTITY = "b,banana, ";
    public static final String LINE_WITHOUT_FRUIT = "b, ,23";
    public static final String LINE_WITHOUT_OPERATION = ",banana,23";
    public static final String LINE_WITH_WRONG_OPERATION = "e,banana,23";
    public static final String FRUIT = "apple";
    public static final int QUANTITY = 200;
    private static List<String> dataFromFile;
    private static TransactionService transactionService;
    private static List<FruitTransaction> fruitTransactionList;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionService = new TransactionServiceImpl();
        dataFromFile = new ArrayList<>();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(transaction);
    }

    @Test
    public void creatListTransaction_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(null));
    }

    @Test
    public void creatListTransaction_emptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(new ArrayList<>()));
    }

    @Test
    public void creatListTransaction_dataWithoutOperation_notOk() {
        dataFromFile.add(LINE_WITHOUT_OPERATION);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_wrongOperation_notOk() {
        dataFromFile.add(LINE_WITH_WRONG_OPERATION);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_withoutFruit_notOk() {
        dataFromFile.add(LINE_WITHOUT_FRUIT);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_withoutQuantity_notOk() {
        dataFromFile.add(LINE_WITHOUT_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_Ok() {
        dataFromFile.add(CORRECT_LINE);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_incorrectFormatCorrectData_Ok() {
        dataFromFile.add(INCORRECT_FORMAT_CORRECT_DATA);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatListTransaction_withColumn_Ok() {
        dataFromFile.add(COLUMN_NAMES_LINE);
        dataFromFile.add(CORRECT_LINE);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @After
    public void tearDown() throws Exception {
        dataFromFile.clear();
    }
}
