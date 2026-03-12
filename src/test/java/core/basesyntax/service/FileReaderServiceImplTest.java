package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFileName_emptyName_notOk() {
        String fileName = "";
        assertThrows(IllegalArgumentException.class, () -> {
            fileReaderService.read(fileName);
        });
    }

    @Test
    public void readFileName_nullName_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReaderService.read(null);
        });
    }

    @Test
    public void readFileName_notExistsName_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read("notExistsFile.csv");
        });
    }

    @Test
    public void readFileName_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read("emptyFile.csv");
        });
    }

    @Test
    public void readFileName_normalFile_ok() {
        assertDoesNotThrow(() -> {
            fileReaderService.read("src/test/resources/emptyFile.csv");
        });
    }
}
