package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int DEFAULT_QUANTITY = 111;
    private static final String HEADER = "type,fruit,quantity";
    private static final String DELIMITER = ",";
    private static final String INVALID_OPERATION = "G";
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String RETURN_OPERATION = "r";
    private static final String SUPPLY_OPERATION = "s";
    private static List<String> testDataList;
    private static TransactionParserService parser;

    @BeforeClass
    public static void beforeAll() {
        parser = new TransactionParserServiceImpl();
        testDataList = new ArrayList<>();
    }

    @Before
    public void beforeEach() {
        testDataList.add(HEADER + System.lineSeparator());
    }

    @Test(expected = RuntimeException.class)
    public void parse_withNullData_notOk() {
        parser.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void parse_withEmptyData_notOk() {
        parser.parse(Collections.emptyList());
    }

    @Test(expected = RuntimeException.class)
    public void parse_withInvalidData_notOk() {
        testDataList.add(INVALID_OPERATION + DELIMITER + BANANA + DELIMITER + DEFAULT_QUANTITY);
        parser.parse(testDataList);
    }

    @Test
    public void parse_withSingleLineValidData_Ok() {
        testDataList.add(BALANCE_OPERATION + DELIMITER + BANANA + DELIMITER + DEFAULT_QUANTITY);
        int expectedSize = 1;
        int actualSize = parser.parse(testDataList).size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void parse_withMultipleLinesValidData_Ok() {
        testDataList.add(BALANCE_OPERATION + DELIMITER + BANANA + DELIMITER + DEFAULT_QUANTITY);
        testDataList.add(BALANCE_OPERATION + DELIMITER + APPLE + DELIMITER + DEFAULT_QUANTITY);
        testDataList.add(RETURN_OPERATION + DELIMITER + APPLE + DELIMITER + DEFAULT_QUANTITY);
        testDataList.add(SUPPLY_OPERATION + DELIMITER + BANANA + DELIMITER + DEFAULT_QUANTITY);
        testDataList.add(PURCHASE_OPERATION + DELIMITER + BANANA + DELIMITER + DEFAULT_QUANTITY);
        int expectedSize = 5;
        int actualSize = parser.parse(testDataList).size();
        assertEquals(expectedSize, actualSize);
    }

    @After
    public void afterAll() {
        testDataList.clear();
    }
}
