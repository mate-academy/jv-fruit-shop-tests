package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String FROM_TEST_FILE_PATH = "src/test/resources/TestFile.csv";
    private static final String FROM_NON_EXISTENT_PATH = "src/test/resources/xxxxxx.csv";

    private FileReaderServiceImpl fileReader = new FileReaderServiceImpl();

    @Test
    void get_readExistentFile_Ok() {
        List<String> expected = List.of(
                "This is a test file for a file reader,",
                "it has multiple lines,",
                "and it should return me as a list of Strings."
        );

        List<String> actual = fileReader.get(FROM_TEST_FILE_PATH);

        assertEquals(expected, actual, "Files are different");
    }

    @Test
    void get_readNonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.get(FROM_NON_EXISTENT_PATH),
                "Non-existing file should throw RuntimeException.");
    }
}
