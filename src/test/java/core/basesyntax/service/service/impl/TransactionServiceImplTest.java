package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
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
    private TransactionService transactionService;
    private List<FruitTransaction> fruitTransactionList;
    private FruitTransaction transaction;

    @Before
    public void setUp() throws Exception {
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
    public void creatListTransactionFromNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(null));
    }

    @Test
    public void creatListTransactionFromEmptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(new ArrayList<>()));
    }

    @Test
    public void creatTransactionWithoutOperation_notOk() {
        dataFromFile.add(LINE_WITHOUT_OPERATION);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransactionWrongOperation_notOk() {
        dataFromFile.add(LINE_WITH_WRONG_OPERATION);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransactionWithoutFruit_notOk() {
        dataFromFile.add(LINE_WITHOUT_FRUIT);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransactionWithoutQuantity_notOk() {
        dataFromFile.add(LINE_WITHOUT_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransaction_Ok() {
        dataFromFile.add(CORRECT_LINE);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransactionIncorrectFormatCorrectData_Ok() {
        dataFromFile.add(INCORRECT_FORMAT_CORRECT_DATA);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @Test
    public void creatTransactionCorrectData_Ok() {
        dataFromFile.add(COLUMN_NAMES_LINE);
        dataFromFile.add(CORRECT_LINE);
        assertEquals(fruitTransactionList,
                transactionService.creatListTransaction(dataFromFile));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        dataFromFile.clear();
    }
}
