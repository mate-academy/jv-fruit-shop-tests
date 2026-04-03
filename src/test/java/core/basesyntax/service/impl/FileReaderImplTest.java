package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        List<String> expected = List.of("line1", "line2");
        Files.write(tempFile, expected);

        List<String> actual = fileReader.read(tempFile.toString());

        assertEquals(expected, actual);
    }

    @Test
    void read_fileNotExist_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read("file_not_exist.csv");
        });
    }
}
