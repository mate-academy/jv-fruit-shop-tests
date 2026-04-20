package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String generateReport(Map<String, Integer> data) {
        if (data.isEmpty() || data.containsKey(null) || data.containsKey("")) {
            throw new IllegalArgumentException("data key must not be empty or null");
        }
        
        if (data.containsValue(null)) {
            throw new IllegalArgumentException("data value must not be empty or null");
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity").append(System.lineSeparator());
        
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IllegalArgumentException("data value must not be negative");
            }
            
            builder.append(entry.getKey()).append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
