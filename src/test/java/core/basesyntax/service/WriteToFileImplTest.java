package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.WriteToFileImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteToFileImplTest {
    private static final String OUTPUT = "src/test/resources/outputFile.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriteToFileImpl();
    }

    @Test
    void writeToFile_checkLists_Ok() {
        List<String> expected = List.of("test1", "test2");
        String data = "test";
        writerService.writeReport(OUTPUT, data);
        try {
            Assertions.assertEquals(expected, Files.readAllLines(Path.of(OUTPUT)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file.");
        }
    }

    @Test
    void writeToFile_null_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeReport(OUTPUT, null));
    }
}
