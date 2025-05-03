package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String NOT_EXISTING_FILE_NAME = "src/test/resources/report.csv";
    private static final String EXISTING_FILE = "src/test/resources/inputFileOk.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_notExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(NOT_EXISTING_FILE_NAME),
                "You should throw an exception if file with the given name does not exist");
    }

    @Test
    void read_existingFile_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        List<String> actual = fileReader.read(EXISTING_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void read_emptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected, actual);

    }

    @Test
    void read_fileNameIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(null),
                "You should throw an exception when file name is null");
    }
}
