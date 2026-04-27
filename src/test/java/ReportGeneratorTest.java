import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private final ReportGenerator generator = new ReportGeneratorImpl();
    
    @Test
    public void generateReport_correctReport_correctData() {
        Map<String, Integer> validData = new LinkedHashMap<>();
        validData.put("banana", 10);
        validData.put("apple", 30);
        
        String expectedReport = "fruit,quantity\nbanana,10\napple,30\n";
        Assertions.assertEquals(expectedReport, generator.generateReport(validData));
    }
    
    @Test
    public void generateReport_throwsException_emptyKey() {
        Map<String, Integer> invalidData = new LinkedHashMap<>();
        invalidData.put("", 10);
        
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(invalidData));
    }
    
    @Test
    public void generateReport_throwsException_nullKey() {
        Map<String, Integer> invalidData = new LinkedHashMap<>();
        invalidData.put(null, 10);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(invalidData));
    }
    
    @Test
    public void generateReport_throwsException_nullValue() {
        Map<String, Integer> invalidData = new LinkedHashMap<>();
        invalidData.put("pineapple", null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(invalidData));
    }
    
    @Test
    public void generateReport_throwsException_invalidData() {
        Map<String, Integer> invalidData = new LinkedHashMap<>();
        invalidData.put("pineapple", -42);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(invalidData));
    }
}
