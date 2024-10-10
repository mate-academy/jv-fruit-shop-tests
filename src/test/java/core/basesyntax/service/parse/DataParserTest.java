package core.basesyntax.service.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitRecord;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParserTest {
    private DataParser dataParser;

    @BeforeEach
    void setUp() {
        dataParser = new DataParserImpl();
    }

    @Test
    void parseFruitRecords_validData_success() {
        List<String> lines = Arrays.asList(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,100",
                "r,orange,10"
        );

        List<FruitRecord> result = dataParser.parseFruitRecords(lines);
        assertEquals(3, result.size());

        FruitRecord bananaTransaction = result.get(0);
        assertEquals(FruitRecord.Operation.BALANCE, bananaTransaction.getOperation());
        assertEquals("banana", bananaTransaction.getFruit());
        assertEquals(20, bananaTransaction.getQuantity());

        FruitRecord appleTransaction = result.get(1);
        assertEquals("apple", appleTransaction.getFruit());
        assertEquals(FruitRecord.Operation.SUPPLY, appleTransaction.getOperation());
        assertEquals(100, appleTransaction.getQuantity());

        FruitRecord orangeTransaction = result.get(2);
        assertEquals("orange", orangeTransaction.getFruit());
        assertEquals(FruitRecord.Operation.RETURN, orangeTransaction.getOperation());
        assertEquals(10, orangeTransaction.getQuantity());
    }

    @Test
    void parseFruitRecords_emptyList_returnEmptyList() {
        List<String> lines = Arrays.asList(
                "operation,fruit,quantity"
        );
        List<FruitRecord> result = dataParser.parseFruitRecords(lines);
        assertTrue(result.isEmpty());
    }

    @Test
    void parseFruitRecords_nullInput_throwsNullPointerException() {
        List<String> lines = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            dataParser.parseFruitRecords(lines);
        });
    }

    @Test
    void parseFruitRecords_invalidQuantity_throwsNumberFormatException() {
        List<String> lines = Arrays.asList(
                "operation,fruit,quantity",
                "b,banana,abc"
        );

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> {
            dataParser.parseFruitRecords(lines);
        });
    }

    @Test
    void parseFruitRecords_invalidOperation_throwsIllegalArgumentException() {
        List<String> lines = Arrays.asList(
                "operation,fruit,quantity",
                "x,apple,50"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataParser.parseFruitRecords(lines);
        });
    }
}
