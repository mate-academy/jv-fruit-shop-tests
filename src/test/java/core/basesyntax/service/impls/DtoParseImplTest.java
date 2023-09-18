package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DtoParser;
import exceptions.IncorrectInputLineException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DtoParseImplTest {
    private List<FruitRecordDto> expected;
    private List<String> testInputString;
    private final DtoParser testParser = new DtoParseImpl();

    @Before
    public void setUp() {
        expected = new ArrayList<>();
    }

    @Test
    public void parsing_emptyList_isOk() {
        assertEquals(expected, testParser.parse(new ArrayList<>()));
    }

    @Test
    public void parsing_correctList_isOk() {
        testInputString = new ArrayList<>();
        testInputString.add("b,banana,20");
        expected.add(new FruitRecordDto("b", "banana", 20));
        assertEquals(expected, testParser.parse(testInputString));
    }

    @Test (expected = IncorrectInputLineException.class)
    public void parsing_incorrectInputLine_throwsException() {
        testInputString = new ArrayList<>();
        testInputString.add("b,banana");
        testParser.parse(testInputString);
    }

    @Test (expected = IncorrectInputLineException.class)
    public void parsing_incorrectOperator_throwsException() {
        testInputString = new ArrayList<>();
        testInputString.add("q,banana,20");
        testParser.parse(testInputString);
    }
}
