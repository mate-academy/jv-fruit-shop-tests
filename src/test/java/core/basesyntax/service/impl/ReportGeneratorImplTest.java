package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.StorageFruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private String report;
    
    @AfterEach
    void tearDown() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void getReport_multipleFruit_ok() {
        StorageFruit.storage.put("banana", 50);
        StorageFruit.storage.put("apple", 30);
        
        report = new ReportGeneratorImpl().getReport();
        
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,50"));
        assertTrue(report.contains("apple,30"));
    }
    
    @Test
    void getReport_emptyStorage_ok() {
        report = new ReportGeneratorImpl().getReport();
        
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, report);
    }
    
    @Test
    void getReport_zeroQuantity_ok() {
        StorageFruit.storage.put("banana", 0);
        
        report = new ReportGeneratorImpl().getReport();
        
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,0"));
    }
}
