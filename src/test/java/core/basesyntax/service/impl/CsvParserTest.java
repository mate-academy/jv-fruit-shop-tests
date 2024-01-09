package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvParserTest {
    private static final List<String> validRawData = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "b,watermelon,30");
    private static Parser csvParser;
    private static List<FruitTransaction> validParsedData;

    @BeforeAll
    static void beforeAll() {
        csvParser = new CsvParser(new FruitTransactionMapper());
        validParsedData = new ArrayList<>();
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20)
        );
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                100)
        );
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "watermelon",
                30)
        );
    }

    @Test
    void parseData_validDataAndSkipHeaderLine_Ok() {
        assertIterableEquals(validParsedData, csvParser.parseData(validRawData));
    }

    @Test
    void parseData_dataIsEmpty_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvParser.parseData(new ArrayList<>());
        });
        assertEquals("Data is empty.", runtimeException.getMessage());
    }

    @Test
    void parseData_dataIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvParser.parseData(null);
        });
        assertEquals("Data must not be null.", runtimeException.getMessage());
    }
}
