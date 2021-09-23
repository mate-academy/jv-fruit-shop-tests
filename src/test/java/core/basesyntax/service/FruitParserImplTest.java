package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static FruitParserImpl fruitParser;
    private static List<String> dataFromFile;
    private static List<FruitRecordDto> actual;
    private static List<FruitRecordDto> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitParser = new FruitParserImpl();
        dataFromFile = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        dataFromFile.add("type,fruit,quantity");
    }

    @Test(expected = RuntimeException.class)
    public void parseData_incorrectData_NotOk() {
        dataFromFile.add("b,banana,-10");
        dataFromFile.add("r,apple");
        fruitParser.apply(dataFromFile);
    }

    @After
    public void tearDown() throws Exception {
        dataFromFile.clear();
    }
}
