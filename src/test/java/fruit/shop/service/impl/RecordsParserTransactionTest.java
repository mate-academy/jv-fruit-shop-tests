package fruit.shop.service.impl;

import fruit.shop.model.FruitTransaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordsParserTransactionTest {
    @Test
    void parseRecords_emptyList_Ok() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = new RecordsParserTransaction().parseRecords(list);
        assertEquals(expected, actual);

    }

    @Test
    void parseRecords_notCorrectOption_notOk() {
        List<String> list = new ArrayList<>();
        list.add("o,fruit,12");
        list.add("l,test,7");
        assertThrows(RuntimeException.class, () -> new RecordsParserTransaction().parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectValue_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b,fruit,three");
        list.add("p,test,two");
        assertThrows(NumberFormatException.class, () -> new RecordsParserTransaction().parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectInput_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b-fruit-three");
        list.add("p-test-two");
        assertThrows(RuntimeException.class, () -> new RecordsParserTransaction().parseRecords(list));
    }

    @Test
    void parseRecords_nullInput_Ok() {
        assertThrows(RuntimeException.class, () -> new RecordsParserTransaction().parseRecords(null));

    }


}