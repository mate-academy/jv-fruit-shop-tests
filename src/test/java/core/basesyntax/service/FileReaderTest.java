package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String VALID_PATH = "src/test/resources/validFile.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/nonExistentFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static FileReader fileReader;
    private static List<String> listOfOkLines;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        listOfOkLines = List.of("b,banana,20", "s,apple,100", "p,banana,100", "r,apple,20");
    }

    @Test
    void read_validFile_Ok() {
        assertEquals(listOfOkLines, fileReader.read(VALID_PATH));
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(NON_EXISTENT_FILE_PATH));
    }

    @Test
    void read_emptyFile_Ok() {
        assertEquals(List.of(), fileReader.read(EMPTY_FILE_PATH));
    }
}
