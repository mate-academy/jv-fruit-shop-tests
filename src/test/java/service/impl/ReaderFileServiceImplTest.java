package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderFileService;

class ReaderFileServiceImplTest {
    private ReaderFileService readerFileService;

    @BeforeEach
    void setUp() {
        readerFileService = new ReaderFileServiceImpl();
    }

    @Test
    void readFromFile_Ok() {
        String fileName = "src/test/resources/input.csv";
        List<String> listFromFile = readerFileService.readFromFile(fileName);
        List<String> writtenLines;
        try {
            writtenLines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(listFromFile, writtenLines);
    }

    @Test
    void readFromFile_NotOk() {
        String fileName = "input1.csv";
        assertThrows(RuntimeException.class, () -> readerFileService.readFromFile(fileName));
    }
}
