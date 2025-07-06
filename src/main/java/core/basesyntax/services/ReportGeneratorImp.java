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
        StringBuilder report = new StringBuilder("fruit,quantity").append(System.lineSeparator());
        storageService.getAll().entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // сортоване за назвою фрукта
                .forEach(entry -> {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        report.append(entry.getKey())
                                .append(",")
                                .append(entry.getValue())
                                .append(System.lineSeparator());
                    }
                });
        return report.toString();
    }
}
