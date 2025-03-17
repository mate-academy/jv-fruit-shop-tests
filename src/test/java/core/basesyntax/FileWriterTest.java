package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.io.FileWriter;
import core.basesyntax.io.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final List<String> INPUT = List.of("b,apple,10");
    private static final String OUTPUT_FILE =
            "target/FileWriterTest.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get(OUTPUT_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void fileWriter_writeFiles_Ok() throws IOException {
        fileWriter.writer(INPUT, OUTPUT_FILE);
        List<String> result = Files.readAllLines(Paths.get(OUTPUT_FILE));

        assertEquals(INPUT, result);
    }

    @Test
    void fileWriter_throwsExceptionIfNotFindFiles_Ok() {
        String location = "wrong/location";
        assertThrows(RuntimeException.class, () -> fileWriter.writer(INPUT, location));
    }

    @Test
    void fileWriter_throwExceptionWhenFileIsCorrupted_Ok() throws IOException {
        Path corruptedFile = Path.of(OUTPUT_FILE);
        Files.createFile(corruptedFile);

        Files.setAttribute(corruptedFile, "dos:readonly", true);

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriter.writer(INPUT, OUTPUT_FILE)
        );

        assertTrue(exception.getMessage().contains("File is readonly"),
                "The expected message was 'File is readonly', but the following was received: "
                        + exception.getMessage());

        Files.setAttribute(corruptedFile, "dos:readonly", false);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(OUTPUT_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
