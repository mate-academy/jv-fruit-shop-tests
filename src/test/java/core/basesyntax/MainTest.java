package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String FINAL_REPORT_PATH_FILE
            = "src/test/java/core/basesyntax/testFinalReport.csv";

    @Test
    void main_ValidInput_ProcessesAndGeneratesReport() throws IOException {
        Main.main(new String[]{});

        Path outputPath = Path.of(FINAL_REPORT_PATH_FILE);
        assertTrue(Files.exists(outputPath), "Final report file should exist.");

        List<String> reportLines = Files.readAllLines(outputPath);

        assertFalse(reportLines.isEmpty(), "Report file should not be empty.");
        assertEquals("fruit,quantity", reportLines.get(0),
                "First line should be the header.");
    }
}
