package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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
        writerService.write("src/test/java/resource/writeSuccessfully.csv","Successfully");
        String readFile = Files.readString(Path.of("src/test/java/resource/writeSuccessfully.csv"));
        assertEquals("Successfully",readFile);
    }

    @Test
    void writeToFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.write("bad/path/to/file.csv", "SomeRepotText"),
                "Should throw runtime exception.");
    }
}
