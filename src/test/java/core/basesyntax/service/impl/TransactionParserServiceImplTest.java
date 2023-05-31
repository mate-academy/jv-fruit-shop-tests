package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserService transactionParserService;
    private static final String ANNOTATION = "type,fruit,quantity";
    private static final String FRUIT_TEST = "apple";
    private static final int AMOUNT_TEST = 48;
    private static final String CODE_OPERATION_TEST = "s";
    private static List<String> listOperation;

    @BeforeClass
    public static void setUp() {
        transactionParserService = new TransactionParserServiceImpl();
        listOperation = new ArrayList<>();
    }

    @After
    public void afterClass() {
        listOperation.clear();
    }

    @Test
    public void parse_Ok() {
        listOperation.add(ANNOTATION);
        listOperation.add(CODE_OPERATION_TEST + "," + FRUIT_TEST + "," + AMOUNT_TEST);
        List<FruitTransaction> actualList =
                transactionParserService.parseToFruitTransaction(listOperation);
        List<FruitTransaction> expectedList =
                List.of(new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, FRUIT_TEST, AMOUNT_TEST));
        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_inputNull_notOk() {
        transactionParserService.parseToFruitTransaction(null);
    }
}
