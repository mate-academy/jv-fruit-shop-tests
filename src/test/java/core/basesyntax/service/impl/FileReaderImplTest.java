package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";

    @Test
    void isFileReader_Ok() {
        FileReader fileReader = new FileReaderImpl();
        List<String> strings = fileReader.readFile(INPUT_FILE_NAME);
        Assertions.assertNotNull(strings.size());
        Assertions.assertThrows(RuntimeException.class,() -> fileReader.readFile(""));
    }
}
