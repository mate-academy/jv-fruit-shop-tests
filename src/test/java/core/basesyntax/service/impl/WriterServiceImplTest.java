package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    
    private final String fileName = "test_report.csv";
    private final Path filePath = Path.of(fileName);
    
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(filePath);
    }
    
    @Test
    void writeTheReport_validInput_ok() throws IOException {
        
        String content = "fruit,quantity\r\nbanana,50\r\n";
        WriterService writerService = new WriterServiceImpl();
        
        writerService.writeTheReport(content, fileName);
        
        assertTrue(Files.exists(filePath));
        String actual = Files.readString(filePath);
        assertEquals(content, actual);
    }
    
    @Test
    void writeTheReport_invalidPath_notOK() {
        
        String content = "something";
        
        String invalidFileName = "con:/test.csv";
        
        WriterService writerService = new WriterServiceImpl();
        
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                writerService.writeTheReport(content, invalidFileName)
        );
        
        assertTrue(ex.getMessage().contains("Error writing the report to file"));
    }
    
    @Test
    void writeTheReport_emptyContent_ok() throws IOException {
        
        String content = "";
        WriterService writerService = new WriterServiceImpl();
        
        writerService.writeTheReport(content, fileName);
        
        assertTrue(Files.exists(filePath));
        String actual = Files.readString(filePath);
        assertEquals(content, actual); // має бути порожній рядок
    }
}
