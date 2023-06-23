package fruit.shop.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import fruit.shop.model.FruitTransaction;
import fruit.shop.service.RecordsParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordsParserTransactionTest {
    private static RecordsParser recordsParser;

    @BeforeAll
    static void beforeAll() {
        recordsParser = new RecordsParserTransaction();
    }

    @Test
    void parseRecords_emptyList_Ok() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = recordsParser.parseRecords(list);
        assertEquals(expected, actual);

    }

    @Test
    void parseRecords_notCorrectOption_notOk() {
        List<String> list = new ArrayList<>();
        list.add("o,fruit,12");
        list.add("l,test,7");
        assertThrows(RuntimeException.class,
                () -> recordsParser.parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectValue_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b,fruit,three");
        list.add("p,test,two");
        assertThrows(NumberFormatException.class,
                () -> recordsParser.parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectInput_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b-fruit-three");
        list.add("p-test-two");
        assertThrows(RuntimeException.class,
                () -> recordsParser.parseRecords(list));
    }

    @Test
    void parseRecords_nullInput_Ok() {
        assertThrows(RuntimeException.class,
                () -> recordsParser.parseRecords(null));
    }
}
