package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_NAME = "src/test/resources/Correct.csv";
    private static final String FILE_WITH_INCORRECT_DATA_NAME = "src/test/resources/Incorrect.csv";
    private static final String NOT_EXISTING_FILE_NAME = "src/test/resources/qwerty.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/Empty.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_inputData_isOk() {
        List<String> expected = List.of("    type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50");
        List<String> actual = fileReader.read(FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void read_incorrectInputData_isOk() {
        List<String> expected = List.of("    type,fruit,quantity",
                "    b2b,ban123ana,2abc0",
                "    b2b,app123le,1abc00",
                "    s2s,ban123ana,1abc00",
                "    p2p,ban123ana,1qbc3",
                "    r2r,app123le,1abc0",
                "    p2p,app123le,2abc0",
                "    p2p,ban123ana,5a",
                "    s2s,ban123ana,5abc0");
        List<String> actual = fileReader.read(FILE_WITH_INCORRECT_DATA_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void read_emptyInputData_isOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectFileName_notOk() {
        List<String> actual = fileReader.read(NOT_EXISTING_FILE_NAME);
    }
}
