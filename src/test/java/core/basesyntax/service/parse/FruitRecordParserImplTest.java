package core.basesyntax.service.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitRecord;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitRecordParserImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int TWENTY_THREE = 23;
    private static final int FOUR = 4;
    private static final int TWELVE = 12;
    private static final int NINE = 9;
    private static final int SEVENTEEN = 17;

    private static FruitRecordParser fruitRecordParser;

    @BeforeAll
    static void setUp() {
        fruitRecordParser = new FruitRecordParserImpl();
    }

    @Test
    void parseFruitRecords_CorrectInput_ReturnsCorrectList() {
        String[] lines = {
                "type,fruit,quantity",
                "b,apple,23",
                "s,banana,12",
                "p,banana,4",
                "r,apple,9",
                "s,apple,17"
        };

        List<FruitRecord> expected = List.of(
                new FruitRecord(FruitRecord.Operation.BALANCE, APPLE, TWENTY_THREE),
                new FruitRecord(FruitRecord.Operation.SUPPLY, BANANA, TWELVE),
                new FruitRecord(FruitRecord.Operation.PURCHASE, BANANA, FOUR),
                new FruitRecord(FruitRecord.Operation.RETURN, APPLE, NINE),
                new FruitRecord(FruitRecord.Operation.SUPPLY, APPLE, SEVENTEEN)
        );
        List<FruitRecord> actual = fruitRecordParser.parseFruitRecords(lines);
        assertEquals(expected, actual);
    }

    @Test
    void parseFruitRecords_EmptyArray_ReturnsEmptyList() {
        String[] lines = {
                "type,fruit,quantity"
        };
        List<FruitRecord> result = fruitRecordParser.parseFruitRecords(lines);
        assertTrue(result.isEmpty());
    }
}
