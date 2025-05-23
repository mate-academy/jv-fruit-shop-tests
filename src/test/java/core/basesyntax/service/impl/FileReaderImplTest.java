package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String correctPath = "reportToReadTest.csv";
    private static final String invalidPath = "C:/invalid_path/file.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_correctPath_Ok() {
        List<String> expect = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");

        assertEquals(expect, fileReader.readFromFile(correctPath));
    }

    @Test
    void readFromFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(invalidPath);
        });
    }
}
