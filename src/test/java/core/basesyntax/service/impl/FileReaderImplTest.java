package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/report.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final List<String> CORRECT_DATA_FROM_FILE = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_correctFailRead_Ok() {
        List<String> actual = fileReader.read(CORRECT_FILE_PATH);
        List<String> expected = CORRECT_DATA_FROM_FILE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void read_incorrectFilePath_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(INCORRECT_FILE_PATH),
                "Expected RuntimeException was not thrown in " + FileReaderImpl.class);
    }

    @Test
    void read_emptyFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(EMPTY_FILE_PATH),
                "Expected RuntimeException was not thrown in " + FileReaderImpl.class);
    }
}
