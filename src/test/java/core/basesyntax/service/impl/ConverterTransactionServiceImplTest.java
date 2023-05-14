package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConverterTransactionServiceImplTest {
    private static ConverterTransactionServiceImpl converterService;
    private static List<String> lineFromFile;

    @BeforeClass
    public static void beforeClass() {
        converterService = new ConverterTransactionServiceImpl();
        lineFromFile = new ArrayList<>();
    }

    @Before
    public void setUp() {
        lineFromFile.add("fruit, quantity");
        lineFromFile.add("b,banana,20");
        lineFromFile.add("b,apple,100");
        lineFromFile.add("s,banana,100");
    }

    @Test(expected = RuntimeException.class)
    public void convertFromString_nullValue_notOk() {
        converterService.convertFromString(null);
    }

    @Test(expected = RuntimeException.class)
    public void convertFromString_invalidQuantityForParsing_notOk() {
        List<String> invalidValue = new ArrayList<>();
        invalidValue.add("fruit, quantity");
        invalidValue.add("b,banana,20s");
        converterService.convertFromString(invalidValue);
    }

    @Test(expected = RuntimeException.class)
    public void convertFromString_negativeQuantity_notOk() {
        List<String> invalidValue = new ArrayList<>();
        invalidValue.add("fruit, quantity");
        invalidValue.add("b,banana,-2");
        converterService.convertFromString(invalidValue);
    }

    @Test(expected = RuntimeException.class)
    public void convertFromString_incorrectNumbersOfColumns_notOk() {
        List<String> invalidValue = new ArrayList<>();
        invalidValue.add("fruit, quantity");
        invalidValue.add("first");
        converterService.convertFromString(invalidValue);
    }

    @Test
    public void convertFromString_successConvert_ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Operation.BALANCE, "banana", 20));
        expected.add(new Transaction(Operation.BALANCE, "apple", 100));
        expected.add(new Transaction(Operation.SUPPLY, "banana", 100));
        List<Transaction> actual = converterService.convertFromString(lineFromFile);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        lineFromFile.clear();
    }
}
