package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportWriterServiceImplTest {

    @Test
    void writeReportToFile() {
        String testFileContent = "apple,10\nbanana,10\n";
        String testPath = "test.txt";
        Path path = Path.of(testPath);

        ReportWriterService reportWriterService = new ReportWriterServiceImpl();

        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            reportWriterService.writeReportToFile(testFileContent, testPath);

            Assertions.assertTrue(Files.exists(path));

            String readContent = Files.readString(path);

            Assertions.assertEquals(readContent, testFileContent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
