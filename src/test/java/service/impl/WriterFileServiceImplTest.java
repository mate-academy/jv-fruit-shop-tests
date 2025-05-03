package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.WriterFileService;

class WriterFileServiceImplTest {
    private WriterFileService writerFileService;
    private List<String> lines = new ArrayList<>();

    @BeforeEach
    void setUp() {
        writerFileService = new WriterFileServiceImpl();
        lines.add("1 line");
        lines.add("2 line");
        lines.add("3 line");
    }

    @Test
    void writeToFile_Ok() {
        String fileName = "output1.csv";
        writerFileService.writeToFile(lines,fileName);
        try {
            List<String> writtenLines = Files.readAllLines(Path.of(fileName));
            assertEquals(lines, writtenLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeToFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerFileService.writeToFile(lines, null));
    }
}
