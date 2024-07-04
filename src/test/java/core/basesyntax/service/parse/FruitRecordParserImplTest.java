package core.basesyntax.service.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitRecord;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitRecordParserImplTest {
    private static FruitRecordParser fruitRecordParser;

    @BeforeAll
    static void setUp() {
        fruitRecordParser = new FruitRecordParserImpl();
    }

    @Test
    public void parseFruitRecords_CorrectInput_ReturnsCorrectList() {
        String[] lines = {
                "type,fruit,quantity",
                "b,apple,23",
                "s,banana,12",
                "p,banana,4",
                "r,apple,9",
                "s,apple,17"
        };

        List<FruitRecord> expected = List.of(
                new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 23),
                new FruitRecord(FruitRecord.Operation.SUPPLY, "banana", 12),
                new FruitRecord(FruitRecord.Operation.PURCHASE, "banana", 4),
                new FruitRecord(FruitRecord.Operation.RETURN, "apple", 9),
                new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", 17)
        );
        List<FruitRecord> actual = fruitRecordParser.parseFruitRecords(lines);
        List<FruitRecord> actualArrayList = List.copyOf(actual);

        assertEquals(expected, actualArrayList);
    }

    @Test
    public void parseFruitRecords_EmptyArray_ReturnsEmptyList() {
        String[] lines = {
                "type,fruit,quantity"
        };
        List<FruitRecord> result = fruitRecordParser.parseFruitRecords(lines);
        assertTrue(result.isEmpty());
    }
}
