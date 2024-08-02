package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String FILE_PATH = "src/test/resources/reportToRead.csv";
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new FileReaderImpl();
    }

    @Test
    void readFromFile_readCorrectFile_ok() {
        List<String> expected = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,apple,300",
                "p,banana,13",
                "r,apple,25"
        );
        List<String> actual = reader.readFromFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_readNonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFromFile("src/test/resources/noReportHere.csv"));
    }

    @Test
    void readFromFile_readNullFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFromFile(null));
    }
}
