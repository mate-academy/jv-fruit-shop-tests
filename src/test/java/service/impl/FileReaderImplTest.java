package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileReader;

class FileReaderImplTest {
    private static final String DATA = "src/main/resources/reportToRead.csv";
    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readFile_fileIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFile(null));
    }

    @Test
    void readFile_filePathIncorrect_NotOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFile("src/main/resources/incorrectReport.csv"));
    }

    @Test
    void readFile_validFile_Ok() {
        List<String> readFile = reader.readFile(DATA);
        assertEquals("    b,banana,27", readFile.get(1));
        assertEquals("    s,banana,53", readFile.get(8));
        assertEquals(9, readFile.size());
    }
}
