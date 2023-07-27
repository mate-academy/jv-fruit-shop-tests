package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterService writerService = new WriterServiceImpl();

    @Test
    void writeData_validInputParameters_ok() throws IOException {
        String report = new StringBuilder().append("banana,152")
                .append(System.lineSeparator()).append("apple,90").toString();
        writerService.writeData(report, "src/test/resources/report.csv");
        int actual = Files.readAllLines(Path.of("src/test/resources/report.csv")).size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void writeData_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.writeData("report", "src/main/resorces/report.csv");
        });
    }

    @Test
    void writeData_nullInputReport_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.writeData(null, "src/main/resources/report.csv");
        });
    }

    @Test
    void writeData_nullInputFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.writeData("report", null);
        });
    }
}
