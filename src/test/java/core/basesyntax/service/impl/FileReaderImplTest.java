package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFile_existentFile_ok() {
        List<String> strings = fileReader.readFile(INPUT_FILE_NAME);
        Assertions.assertNotNull(strings.size());
    }

    @Test
    void readFile_nonExistentFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader
                .readFile("FileNotExist"));
    }
}
