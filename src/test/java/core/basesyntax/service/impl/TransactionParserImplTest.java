package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParserImpl transactionParserImpl;
    private static List<String> testData;

    @BeforeAll
    static void beforeAll() {
        transactionParserImpl = new TransactionParserImpl();
        testData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "d,apple,50");
    }

    @Test
    void testParseData_Ok() {
        assertTrue(transactionParserImpl.parseData(new ArrayList<>()).isEmpty());
    }

    @Test
    void parseFruitTransactionNotValidData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> transactionParserImpl.parseData(testData));
    }
}
