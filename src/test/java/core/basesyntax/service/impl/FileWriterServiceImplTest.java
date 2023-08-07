package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static FileWriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new FileWriterServiceImpl();
    }

    @Test
    void writeToFile_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String validFilePath = "test_valid_report.csv";
        writerService.writeToFile(validFilePath,report);
        Path path = Paths.get(validFilePath);
        List<String> strings = null;
        try (Stream<String> lines = Files.lines(path)) {
            strings = lines.collect(Collectors.toList());
        } catch (IOException e) {
            fail("Exсeption shouldn't be thrown in this test");
        }
        String[] transactions = report.split(System.lineSeparator());
        for (int i = 0; i < strings.size(); i++) {
            String expected = strings.get(i);
            assertEquals(expected,transactions[i],
                    "transactions differ at element: " + i);
        }
    }

    @Test
    void writeToFile_null_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, ""),
                "method should throw exception whe path is null");
        String expected = "file Path can't be null";
        assertEquals(expected,runtimeException.getMessage());
    }

}
