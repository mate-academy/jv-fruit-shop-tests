package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.parser.impl.CsvWriteParserImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteParserTest {
    private static WriteParser writeParser;
    private static final String TYPE_QUANTITY = "type,quantity" + System.lineSeparator();
    private Map<String, Integer> validData;

    @BeforeAll
    static void beforeAll() {
        writeParser = new CsvWriteParserImpl();
    }

    @BeforeEach
    public void beforeEach() {
        validData = new HashMap<>();
        validData.put("banana", 30);
        validData.put("apple", 20);
    }

    @Test
    public void parseProcessedData_validData_ok() {
        String expected = TYPE_QUANTITY + "banana,30" + System.lineSeparator() + "apple,20";
        String actual = writeParser.parseProcessedData(validData);
        assertEquals(expected, actual);
    }

    @Test
    public void parseProcessedData_emptyData_ok() {
        String actual = writeParser.parseProcessedData(new HashMap<>());
        assertEquals(TYPE_QUANTITY, actual);
    }

    @Test
    public void parseProcessedData_null_notOk() {
        assertThrows(NullPointerException.class, () -> writeParser.parseProcessedData(null));
    }
}
