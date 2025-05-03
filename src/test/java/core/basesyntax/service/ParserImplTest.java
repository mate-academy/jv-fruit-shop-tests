package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static final int EXPECTED_LENGTH = 8;
    private static Parser parser;
    private static List<String> input;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
        input = new ArrayList<>();
    }

    @Test
    public void parseDto_ValidFile_Ok() {
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        input.add("r,apple,10");
        input.add("p,apple,20");
        input.add("p,banana,5");
        input.add("s,banana,50");
        assertEquals(EXPECTED_LENGTH, parser.parseDto(input).size());
    }

    @Test
    public void parseDto_FileWithSpaces_Ok() {
        input.add("type,fruit,quantity");
        input.add("b  ,banana,20");
        input.add("b,apple  ,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        input.add("r,  apple,10");
        input.add("p,apple,20  ");
        input.add("p,banana,5");
        input.add("s,  banana,50");
        assertEquals(EXPECTED_LENGTH, parser.parseDto(input).size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseDto_FileWithInvalidData_NotOk() {
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana");
        parser.parseDto(input);
    }

    @After
    public void tearDown() {
        input.clear();
    }
}
