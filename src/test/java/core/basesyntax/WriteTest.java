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

    @BeforeEach
    void create() throws IOException {
        writerService = new WriterServiceImpl();
        outputDir = Paths.get("target/test-output");
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
        assertThrows(IllegalArgumentException.class, () ->
                writerService.writeReport(filePath, null));
    }

    @Test
    void writeReport_withInvalidFilePath_throwsException() {
        String report = "fruit,quantity\nbanana,100\napple,50";
        String invalidFilePath = outputDir.resolve("invalidDir").resolve("report.csv").toString();
        assertThrows(RuntimeException.class, () ->
                writerService.writeReport(report, invalidFilePath));

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
