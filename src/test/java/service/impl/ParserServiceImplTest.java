package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ParserService;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseLines_validList_Ok() {
        final List<String> data = Arrays.asList("type,fruit,quantity", "b,banana,20", "s,apple,100",
                "p,apple,50");
        final List<FruitTransaction> actual = parserService.parseLines(data);
        final String expected1 = "BALANCEbanana20";
        final String expected2 = "SUPPLYapple100";
        final String expected3 = "PURCHASEapple50";
        assertEquals(3, actual.size());
        assertEquals(expected1, new StringBuilder().append(actual.get(0).getOperation())
                .append(actual.get(0).getFruit()).append(actual.get(0).getQuantity()).toString());
        assertEquals(expected2, new StringBuilder().append(actual.get(1).getOperation())
                .append(actual.get(1).getFruit()).append(actual.get(1).getQuantity()).toString());
        assertEquals(expected3, new StringBuilder().append(actual.get(2).getOperation())
                .append(actual.get(2).getFruit()).append(actual.get(2).getQuantity()).toString());
    }

    @Test
    void parseLines_listNull_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseLines(null));
    }
}
