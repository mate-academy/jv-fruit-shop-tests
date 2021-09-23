package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String INPUT_FILEPATH
            = "src/main/java/core/basesyntax/resources/input.csv";
    private static final String FALSE_INPUT_FILEPATH
            = "src/main/java/core/baseax/resources/input.csv";
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readFromFile(INPUT_FILEPATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_FalseInputPath_ThrowExc_NotOk() {
        fileReader.readFromFile(FALSE_INPUT_FILEPATH);
    }
}
