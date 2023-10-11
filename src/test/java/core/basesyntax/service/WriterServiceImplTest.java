package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
    void setUp() {
        writerService = new WriterServiceImpl();
        try {
            tempDir = Files.createTempDirectory("tempDir");
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        outputPath = tempDir.resolve("tempReport.csv").toString();
    }

    @Test
    public void writeData_fileCreated_ok() {
        WriterServiceImpl writer = new WriterServiceImpl();
        writer.writeData(DATA, outputPath);
        Assertions.assertTrue(Files.exists(Path.of(outputPath)));

    }

    @Test
    public void writeData_correctContent_ok() {
        WriterServiceImpl writer = new WriterServiceImpl();
        writer.writeData(DATA, outputPath);
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(outputPath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read \"tempReport.csv\"", e);
        }
        Assertions.assertEquals(DATA, fileContent);
    }

    @Test
    void writeData_notExistFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> writerService.writeData("Test Data",
                "nonexistent/test.csv"));
    }
}
