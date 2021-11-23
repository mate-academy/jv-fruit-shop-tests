package core.basesyntax.fruitshop.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InputDataReaderImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/testIncomeFile";
    private InputDataReaderImpl reader;
    private List<String> expected;
    private List<String> actual;

    @Before
    public void setUp() throws Exception {
        reader = new InputDataReaderImpl();
        expected = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void wrongPath_readFromFile_Ok() {
        reader.readFromFile("wrong/path");
    }

    @Test
    public void validFilePath_readFromFile_Ok() {
        actual = reader.readFromFile(INPUT_FILE_PATH);
        expected = List.of(" type,fruit,quantity", " b,banana,10",
                " b,apple,80", " s,banana,100", " p,banana,13");
        assertEquals(expected,actual);
    }
}
