package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionServiceImplTest {
    public static final String HEADER_LINE = "type,fruit,quantity";
    public static final String CORRECT_LINE = "b,apple,200";
    public static final String INVALID_DATA_FORMAT = "b-  , apple?,  200";
    public static final String EMPTY_QUANTITY_LINE = "b,banana, ";
    public static final String EMPTY_FRUIT_LINE = "b, ,23";
    public static final String EMPTY_OPERATION_LINE = ",banana,23";
    public static final String INVALID_OPERATION_LINE = "e,banana,23";
    public static final String DEFAULT_FRUIT = "apple";
    public static final int DEFAULT_QUANTITY = 200;
    private static List<String> dataFromFile;
    private static TransactionService transactionService;
    private static List<FruitTransaction> fruitTransactionList;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionService = new TransactionServiceImpl();
        dataFromFile = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(DEFAULT_FRUIT);
        transaction.setQuantity(DEFAULT_QUANTITY);
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(transaction);
    }

    @Before
    public void setUp() {
        dataFromFile.add(HEADER_LINE);
    }

    @Test
    public void createListTransaction_null_notOk() {
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(null);
    }

    @Test
    public void createListTransaction_emptyList_notOk() {
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(new ArrayList<>());
    }

    @Test
    public void createListTransaction_emptyOperation_notOk() {
        dataFromFile.add(EMPTY_OPERATION_LINE);
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(dataFromFile);
    }

    @Test
    public void createListTransaction_emptyFruit_notOk() {
        dataFromFile.add(EMPTY_FRUIT_LINE);
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(dataFromFile);
    }

    @Test
    public void createListTransaction_emptyQuantity_notOk() {
        dataFromFile.add(EMPTY_QUANTITY_LINE);
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(dataFromFile);
    }

    @Test
    public void createListTransaction_invalidOperation_notOk() {
        dataFromFile.add(INVALID_OPERATION_LINE);
        thrown.expect(RuntimeException.class);
        transactionService.createListTransaction(dataFromFile);
    }

    @Test
    public void createListTransaction_invalidFormat_Ok() {
        dataFromFile.add(INVALID_DATA_FORMAT);
        assertEquals(fruitTransactionList,
                transactionService.createListTransaction(dataFromFile));
    }

    @Test
    public void createListTransaction_Ok() {
        dataFromFile.add(CORRECT_LINE);
        assertEquals(fruitTransactionList,
                transactionService.createListTransaction(dataFromFile));
    }

    @After
    public void tearDown() {
        dataFromFile.clear();
    }
}
