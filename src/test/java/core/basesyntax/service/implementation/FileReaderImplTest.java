package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final FileReader FILE_READER = new FileReaderImpl();
    private static final String FILE_NAME = "src/test/resources/CorrectFruitInput.csv";
    private static final String INCORRECT_FILE_NAME = "src/test/resources/IncorrectFruitInput.csv";
    private static final String INCORRECT_NAME = "src/test/resources/IncorrectName.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/EmptyFruitInput.csv";

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
        List<String> actual = FILE_READER.read(FILE_NAME);
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
        List<String> actual = FILE_READER.read(INCORRECT_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void read_emptyInputData_isOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = FILE_READER.read(EMPTY_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectFileName_notOk() {
        List<String> actual = FILE_READER.read(INCORRECT_NAME);
    }
}
