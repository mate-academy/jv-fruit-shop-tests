package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileParserServiceImplTest {
    private static FileParserService recordsParser;

    @BeforeAll
    static void beforeAll() {
        recordsParser = new FileParserServiceImpl();
    }

    @Test
    void parse_emptyList_Ok() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = recordsParser.parse(list);
        assertEquals(expected, actual);
    }

    @Test
    void parse_invalidOption_notOk() {
        List<String> list = new ArrayList<>();
        list.add("x,banana,12");
        list.add("y,apple,7");
        assertThrows(RuntimeException.class,
                () -> recordsParser.parse(list));
    }

    @Test
    void parse_invalidValue_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,value");
        list.add("p,apple,value1");
        assertThrows(NumberFormatException.class,
                () -> recordsParser.parse(list));
    }

    @Test
    void parse_invalidInput_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b-fruit1-value");
        list.add("p-fruit2-value2");
        assertThrows(RuntimeException.class,
                () -> recordsParser.parse(list));
    }

    @Test
    void parse_nullInput_Ok() {
        assertThrows(RuntimeException.class,
                () -> recordsParser.parse(null));
    }
}
