package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private static ParserService parserService;
    private static List<String> data;
    private static List<FruitTransaction> expectedList;
    private static List<String> nullElementList;
    private static List<String> notValidList;

    @BeforeAll
    public static void setUp() {
        parserService = new ParserServiceImpl();

        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");

        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction("b", "banana", 20));
        expectedList.add(new FruitTransaction("b", "apple", 100));

        nullElementList = new ArrayList<>();
        nullElementList.add("type,fruit,quantity");
        nullElementList.add(null);

        notValidList = new ArrayList<>();
        notValidList.add("type,fruit,quantity");
        notValidList.add("apple,100");
    }

    @Test
    void parseData_emptyDataList_notOk() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> parserService.parseData(new ArrayList<>()));
    }

    @Test
    void parseData_notValidDataList_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseData(null));
        assertThrows(NullPointerException.class, () -> parserService.parseData(nullElementList));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> parserService.parseData(notValidList));
    }

    @Test
    void parseData_allOk() {
        List<FruitTransaction> actualList = assertDoesNotThrow(() -> parserService.parseData(data));

        assertEquals(expectedList, actualList);
    }
}
