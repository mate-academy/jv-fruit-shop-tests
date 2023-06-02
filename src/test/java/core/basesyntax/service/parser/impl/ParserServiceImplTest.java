package core.basesyntax.service.parser.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.parser.ParserService;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static final int COUNT_OF_ELEMENT_AFTER_PARSING = 1;
    private static final String TITLE_OF_INPUT_DATA = "type,fruit,quantity";
    private static ParserService parserService;

    @BeforeAll
    static void setUpBeforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseData_withValidData_ok() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                "b,banana,20");
        List<FruitTransaction> resultFruitTransactions = parserService.parseData(sourceData);
        assertEquals(COUNT_OF_ELEMENT_AFTER_PARSING, resultFruitTransactions.size());
    }

    @Test
    void parseData_withWrongEnumCode_notOk() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                "invalidCode,banana,20");
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> parserService.parseData(sourceData));
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void parseData_withEmptyEnumCode_notOk() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                ",banana,20");
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> parserService.parseData(sourceData));
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void parseData_withNull_notOk() {
        List<String> sourceData = null;
        assertThrows(NullPointerException.class,
                () -> parserService.parseData(sourceData));
    }

    @Test
    void parseData_stringIsEmpty_notOk() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                "");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> parserService.parseData(sourceData));
        assertEquals("String cannot be null or empty.", exception.getMessage());
    }

    @Test
    void parseData_stringWithoutFruit_notOk() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                "b,20");
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                () -> parserService.parseData(sourceData));
        assertEquals("Index 2 out of bounds for length 2", exception.getMessage());
    }

    @Test
    void parseData_stringWithoutQuantity_notOk() {
        List<String> sourceData = List.of(TITLE_OF_INPUT_DATA,
                "b,banana");
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                () -> parserService.parseData(sourceData));
        assertEquals("Index 2 out of bounds for length 2", exception.getMessage());
    }
}
