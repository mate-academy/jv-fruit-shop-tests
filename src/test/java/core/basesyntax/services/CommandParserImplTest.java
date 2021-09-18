package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CommandParserImplTest {
    private static List<String> dataFromFile;
    private static CommandParser commandParser;
    private static final String TITLE = "type,fruit,quantity";
    private static final String BANANA = "b,banana,20";
    private static final String APPLE = "b,apple,100";

    @Before
    public void setUp() {
        dataFromFile = new ArrayList<>();
        dataFromFile.add(TITLE);
        dataFromFile.add(BANANA);
        dataFromFile.add(APPLE);
        commandParser = new CommandParserImpl();
    }

    @Test
    public void outputDataNotContainTitle_Ok() {
        int actual = commandParser.parseData(dataFromFile).size();
        int expected = 2;
        assertEquals("Test failed: wrong size", actual,expected);
    }

    @Test
    public void parseData_Ok() {
        Operation actual = commandParser.parseData(dataFromFile).get(0);
        Operation expected = new Operation("b",new Fruit("banana"), 20);
        assertEquals("Test failed: wrong operation",actual,expected);
    }
}
