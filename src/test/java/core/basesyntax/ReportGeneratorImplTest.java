//package core.basesyntax;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import core.basesyntax.shopserviceandreportgenerator.ReportGenerator;
//import core.basesyntax.shopserviceandreportgenerator.ReportGeneratorImpl;
//import java.util.HashMap;
//import java.util.Map;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class ReportGeneratorImplTest {
//    private ReportGenerator reportGenerator;
//    private Map<String, Integer> storage;
//
//    @BeforeEach
//    void setUp() {
//        reportGenerator = new ReportGeneratorImpl();
//        storage = new HashMap<>();
//    }
//
//    @Test
//    void getReport_shouldGenerateCorrectReport() {
//        storage.put("apple", 100);
//        storage.put("banana", 50);
//        String report = reportGenerator.getReport(storage);
//        String expected = "fruit,quantity\napple,100\nbanana,50\n";
//        assertEquals(expected, report);
//    }
//}

