package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

public class ReportGeneratorImplTest {
    @AfterEach
    void cleanUp() {
        Storage.fruits.clear();
    }

    @Test
    void testReport_Ok() {
        Storage.fruits.put("apple", 30);
        Storage.fruits.put("orange", 15);

        String header = "fruit,amount";
        StringBuilder builder = new StringBuilder();
        builder.append(header).append(System.lineSeparator())
                .append("apple,30").append(System.lineSeparator())
                .append("orange,15").append(System.lineSeparator());

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        assertEquals(builder.toString(), reportGenerator.getReport());
    }

    @Test
    void testReportWithEmptyStorage_Ok() {
        String header = "fruit,amount";

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        assertEquals(header + System.lineSeparator(), reportGenerator.getReport());
    }
}
