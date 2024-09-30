package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader reader = new FileReaderImpl();
    private Path tempFile;

    @BeforeEach
    void beforeEach() {
        try {
            tempFile = Files.createTempFile("test", ".csv");
            Files.write(tempFile, List.of("type,fruit,quantity", "b,apple,4"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void read_nullFile_notOk() {
        String nullName = null;
        Assertions.assertThrows(RuntimeException.class, () -> reader.read(nullName));
    }

    @Test
    void read_notExistingFile_notOk() {
        String fileName = "notExistingFile.csv";
        Assertions.assertThrows(RuntimeException.class, () -> reader.read(fileName));
    }

    @Test
    void read_correctFile_Ok() {
        try {
            Files.write(tempFile, List.of("type,fruit,quantity", "b,apple,4"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expectedList = List.of("type,fruit,quantity",
                "b,apple,4");

        List<String> actualList = reader.read(String.valueOf(tempFile));
        Assertions.assertEquals(expectedList, actualList);
    }
}
