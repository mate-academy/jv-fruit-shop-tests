package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeInFile_allCorrect_Ok() throws IOException {
        writerService.writeToFile("src/test/java/resources/record_to_file.csv","Success");
        String readFile = Files.readString(Path.of("src/test/java/resources/record_to_file.csv"));
        assertEquals("Success",readFile);
    }

    @Test
    void writeToFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("src/test/java/resource/file.csv", "SomeRepotText"),
                "Should throw runtime exception.");
    }
}
