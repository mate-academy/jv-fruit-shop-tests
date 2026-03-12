package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_emptyReport_notOk() {
        String report = "";
        String fileName = "final";
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(report, fileName);
        });
    }

    @Test
    public void write_nullReport_notOk() {
        String fileName = "final";
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(null, fileName);
        });
    }

    @Test
    public void write_emptyFileName_notOk() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,102";
        String fileName = "";
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(report, fileName);
        });
    }

    @Test
    public void write_nullFileName_notOk() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,102";
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(report, null);
        });
    }

    @Test
    public void write_normalFileName_ok() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,102";
        assertDoesNotThrow(() -> {
            fileWriterService.write(report,"src/test/resources/writeFile.csv");
        });
    }
}
