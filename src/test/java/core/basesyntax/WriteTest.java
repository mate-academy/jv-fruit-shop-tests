package core.basesyntax;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.WriterService;
import service.impl.WriterServiceImpl;

public class WriteTest {
    private static WriterService writerService;
    private static Path outputDir;
    private static final String pathToFile = "/test/resources";

    @BeforeEach
    void create() throws IOException {
        writerService = new WriterServiceImpl();
        outputDir = Paths.get(pathToFile);
        Files.createDirectories(outputDir);
    }

    @Test
    void writeReport_withValidData_writesCorrectly() throws IOException {
        String report = "fruit,quantity\nbanana,152\napple,90";
        String filePath = getResourcePath("validDataActual.csv");
        writerService.writeReport(report,filePath);
        assertEquals(report, Files.readString(Path.of(filePath)).trim());
    }

    @Test
    void writeReport_withNullFilePath_throwsException() {
        String filePath = "fruit,quantity\nbanana,100\napple,50";
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> writerService
                    .writeReport(filePath, null));
        assertEquals("File cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void writeReport_withInvalidFilePath_throwsException() {
        String report = "fruit,quantity\nbanana,100\napple,50";
        String invalidFilePath = outputDir.resolve("testResult.csv")
                .resolve("report.csv").toString();
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                writerService.writeReport(report, invalidFilePath));
        assertEquals("Failed to write report", exception.getMessage());
    }

    public String getResourcePath(String fileName) {
        try {
            return Paths.get(this.getClass().getClassLoader()
                    .getResource(fileName)
                    .toURI())
                .toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
