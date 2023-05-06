package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.service.ReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private final ReadService readService = new ReadServiceImpl();
    private Path tempFile;
    
    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test", ".csv");
    }
    
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }
    
    @Test
    void add_correctData_Ok() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,100", "s,banana,20"));
        List<String> actual = readService.readFile(tempFile);
        List<String> expected = List.of("b,banana,100", "s,banana,20");
        assertEquals(expected, actual);
    }
    
    @Test
    void add_OperationNull_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "null,banana,100", "s,banana,20"));
        assertThrows(ValidationException.class, () -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_OperationEmpty_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", ",banana,100", "s,banana,20"));
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_FruitNull_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,null,100", "s,banana,20"));
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_FruitEmpty_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,,100", "s,banana,20"));
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_QuantityNull_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,null", "s,banana,20"));
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_QuantityEmpty_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,", "s,banana,20"));
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void add_EmptyFile_NotOk() {
        assertThrows(ValidationException.class,() -> {
            readService.readFile(tempFile);
        });
    }
    
    @Test
    void readFile_InvalidPath_NotOk() {
        Path invalidPath = Path.of("nonFile.csv");
        assertThrows(RuntimeException.class, () -> readService.readFile(invalidPath));
    }
}
