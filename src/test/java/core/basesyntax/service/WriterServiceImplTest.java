package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String DATA =
            "fruit,quantity\n" + "banana,152" + System.lineSeparator()
                    + "apple," + "90" + System.lineSeparator();
    private WriterServiceImpl writerService;
    private Path tempDir;
    private String outputPath;

    @BeforeEach
    void setUp() throws IOException {
        writerService = new WriterServiceImpl();
        tempDir = Files.createTempDirectory("tempDir");
        outputPath = tempDir.resolve("tempReport.csv").toString();
    }

    @Test
    public void writeData_FileCreated_Ok() {
        writerService.writeData(DATA, outputPath);
        assertTrue(Files.exists(Path.of(outputPath)));
    }

    @Test
    public void writeData_CorrectContent_Ok() {
        assertDoesNotThrow(() -> {
            writerService.writeData(DATA, outputPath);
            String fileContent = Files.readString(Path.of(outputPath));
            assertEquals(DATA, fileContent);
        }, "Can't read \"tempReport.csv\"");
    }

    @Test
    void writeData_notExistFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> writerService.writeData("Test Data",
                "nonexistent/test.csv"));
    }
}
