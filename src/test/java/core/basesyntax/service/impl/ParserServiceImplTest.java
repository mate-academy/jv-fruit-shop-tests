package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parser;
    
    @BeforeAll
    static void beforeAll() {
        parser = new ParserServiceImpl();
    }

    @Test
    void parseInputData_createCorrectListFromInputData_Ok() {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        List<String[]> actual =
                parser.parseInputData(List.of("type,fruit,quantity", "    b,banana,20",
                "    b,apple,100", "    s,banana,100", "    p,banana,13", "    r,apple,10",
                "    p,apple,20", "    p,banana,5", "    s,banana,50"));
        actual.stream().map(act -> act[0]).forEach(a -> builder1.append(a).append("-"));
        actual.stream().map(act -> act[1]).forEach(a -> builder2.append(a).append("-"));
        actual.stream().map(act -> act[2]).forEach(a -> builder3.append(a).append("-"));
        String expectedOperationsStack = "b-b-s-p-r-p-p-s-";
        String expectedFruitStack = "banana-apple-banana-banana-apple-apple-banana-banana-";
        String expectedValueStack = "20-100-100-13-10-20-5-50-";
        assertEquals(expectedOperationsStack, builder1.toString());
        assertEquals(expectedFruitStack, builder2.toString());
        assertEquals(expectedValueStack, builder3.toString());
    }
}
