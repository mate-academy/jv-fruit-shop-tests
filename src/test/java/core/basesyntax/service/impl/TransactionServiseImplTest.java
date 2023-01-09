package core.basesyntax.service.impl;

import static core.basesyntax.operation.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitsTranslation;
import core.basesyntax.operation.Operation;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TransactionServiseImplTest {
    private static final String TEST_FRUIT_FIRST = "apple";
    private static final String TEST_FRUIT_SECOND = "banana";
    private static final String TITLE_FILE = "type,fruit,quantity";
    private static final String FIRST_LINE_FILE = "b,apple,50";
    private static final String SECOND_LINE_FILE = "b,banana,100";
    private static final int TEST_BALANCE_FIRST = 50;
    private static final int TEST_BALANCE_SECOND = 100;
    private static final Operation TEST_OPERATION = BALANCE;
    private List<FruitsTranslation> fruitTransactionsList;
    private TransactionServiseImpl fruitTransactionParser;

    @Before
    public void setUp() {
        fruitTransactionParser = new TransactionServiseImpl();
        FruitsTranslation fruitTransactionFirst = new FruitsTranslation(TEST_OPERATION,
                TEST_FRUIT_FIRST, TEST_BALANCE_FIRST);
        FruitsTranslation fruitTransactionSecond = new FruitsTranslation(TEST_OPERATION,
                TEST_FRUIT_SECOND, TEST_BALANCE_SECOND);
        fruitTransactionsList = new ArrayList<>();
        fruitTransactionsList.add(fruitTransactionFirst);
        fruitTransactionsList.add(fruitTransactionSecond);
    }

    @After
    public void tearDown() {
        fruitTransactionsList = null;
    }

    @Test
    public void getFruitTransactionList_Work_Ok() {
        List<String> dataFromCsv = new ArrayList<>();
        dataFromCsv.add(TITLE_FILE);
        dataFromCsv.add(FIRST_LINE_FILE);
        dataFromCsv.add(SECOND_LINE_FILE);
        List<FruitsTranslation> actual = fruitTransactionParser.transactionProcess(dataFromCsv);
        Assertions.assertEquals(fruitTransactionsList, actual);
    }

    @Test
    public void transactionService_Empty_Value_NotOK() {
        TransactionService transactionService = new TransactionServiseImpl();
        assertNotNull(transactionService);
        List<String> data = null;
        assertThrows(RuntimeException.class, () -> transactionService.transactionProcess(data));
    }
}
