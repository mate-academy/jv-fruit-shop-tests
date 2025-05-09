package core.basesyntax.services;

import java.util.Map;

public class ReportGeneratorImp implements ReportGenerator {
    private static final String COMMA = ",";
    private static final String HEADER = "fruit,quantity";
    private final StorageService storageService;

    public ReportGeneratorImp(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public String generateReport() {

        Map<String, Integer> fruits = storageService.getAll();
        StringBuilder report = new StringBuilder();
        report.append(HEADER).append(System.lineSeparator());
        if (fruits == null) {
            return HEADER + System.lineSeparator();
        }
        for (Map.Entry<String, Integer> entry : fruits.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            report.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return report.toString();
    }
}
