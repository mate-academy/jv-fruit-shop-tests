package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class WriterServiceTest extends FruitShopTest {

    @Test
    public void writeReportToFile_OK() {
        String reportText = "Some report";
        File reportFile = writerService.writeReportToFile(reportText);
        assertTrue(reportFile.exists());
        try {
            assertEquals(reportText, new String(Files.readAllBytes(reportFile.toPath())));
            reportFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
