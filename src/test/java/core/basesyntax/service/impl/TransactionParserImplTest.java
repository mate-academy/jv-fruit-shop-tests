package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private final TransactionParserImpl transactionParserImpl = new TransactionParserImpl();

    @Test
    void testParseData_Ok() {
        assertTrue(transactionParserImpl.parseData(new ArrayList<>()).isEmpty());
    }

    @Test
    void parseFruitTransactionNotValidData_NotOk() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "d,apple,50"));
        assertThrows(RuntimeException.class,
                () -> transactionParserImpl.parseData(testData));
    }
}
