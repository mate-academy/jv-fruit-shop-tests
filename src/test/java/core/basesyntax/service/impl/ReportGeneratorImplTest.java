package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.StorageFruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static String report;
    private static ReportGeneratorImpl reportGenerator;
    
    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }
    
    @AfterEach
    void tearDown() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void getReport_multipleFruit_ok() {
        StorageFruit.storage.put("banana", 50);
        StorageFruit.storage.put("apple", 30);
        
        report = reportGenerator.getReport();
        
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,50"));
        assertTrue(report.contains("apple,30"));
    }
    
    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        
        report = reportGenerator.getReport();
        
        assertEquals(expected, report);
    }
    
    @Test
    void getReport_zeroQuantity_ok() {
        StorageFruit.storage.put("banana", 0);
        
        report = reportGenerator.getReport();
        
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,0"));
    }
}
