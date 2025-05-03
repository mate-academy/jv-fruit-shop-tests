package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.fruit.Operation;
import core.basesyntax.model.fruit.Record;
import core.basesyntax.service.ReadParser;
import core.basesyntax.service.impl.CsvReadParserImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadParserTest {
    private static ReadParser readParser;
    private List<String> validData;
    private List<String> invalidOperationsData;

    @BeforeAll
    static void beforeAll() {
        readParser = new CsvReadParserImpl();
    }

    @BeforeEach
    void beforeEach() {
        validData = new ArrayList<>();
        validData.add("type,fruit,quantity");
        validData.add("b,banana,20");
        validData.add("b,apple,100");
        invalidOperationsData = new ArrayList<>();
        invalidOperationsData.add("type,fruit,quantity");
        invalidOperationsData.add("q,banana,20");
        invalidOperationsData.add("f,apple,100");
    }

    @Test
    public void parseFileData_validData_ok() {
        List<Record> expected = List.of(
                new Record(Operation.BALANCE, "banana", 20),
                new Record(Operation.BALANCE, "apple", 100));
        List<Record> actual = readParser.parseFileData(validData);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFileData_invalidOperation_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> readParser.parseFileData(invalidOperationsData));
    }

    @Test
    public void parseFileData_emptyList_ok() {
        List<Record> records = readParser.parseFileData(new ArrayList<>());
        assertEquals(records, Collections.emptyList());
    }

    @Test
    public void parseFileData_null_notOk() {
        assertThrows(NullPointerException.class, () -> readParser.parseFileData(null));
    }
}
