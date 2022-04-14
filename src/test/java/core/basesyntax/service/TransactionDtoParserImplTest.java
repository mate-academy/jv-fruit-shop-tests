package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoParserImplTest {
    private static TransactionDtoParser transactionDtoParser;
    private static List<String> dataFromFile;
    private static List<TransactionDto> actual;
    private static List<TransactionDto> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionDtoParser = new TransactionDtoParserImpl();
        dataFromFile = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        dataFromFile.add("type,fruit,quantity");
    }

    @Test
    public void parseData_ok() {
        dataFromFile.add("b,banana,10");
        dataFromFile.add("r,apple,120");
        expected.add(new TransactionDto("b", new Fruit("banana"), 10));
        expected.add(new TransactionDto("r", new Fruit("apple"), 120));
        actual = transactionDtoParser.parseData(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_incorrectData_NotOk() {
        dataFromFile.add("b,banana,-10");
        dataFromFile.add("r,apple");
        transactionDtoParser.parseData(dataFromFile);
    }

    @After
    public void tearDown() throws Exception {
        dataFromFile.clear();
    }
}
