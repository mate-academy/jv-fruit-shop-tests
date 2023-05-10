package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterServiceImplTest {
    @BeforeAll
    static void beforeAll() {
        WriterService writerService = new WriterServiceImpl();
        String filePath = "src/test/resources/data3.txt";
        StringBuilder report = new StringBuilder().append("fruit,quantity")
                .append(System.lineSeparator()).append("banana,100")
                .append(System.lineSeparator()).append("apple,15");
        writerService.createReportFile(report.toString(), filePath);
    }

    @Test
    void createReportFile_Ok() {
        try {
            List<String> actual = Files.readAllLines(Path.of("src/test/resources/data3.txt"));
            List<String> expected = new ArrayList<>();
            expected.add("fruit,quantity");
            expected.add("banana,100");
            expected.add("apple,15");
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void createReportFile_WrongPath_NotOk() {
        assertThrows(IOException.class, () -> Files.readAllLines
                (Path.of("src/test/resources/data0.txt")));
    }

    @Test
    void createReportFile_PathIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> Files.readAllLines
                (Path.of(null)));
    }
}
