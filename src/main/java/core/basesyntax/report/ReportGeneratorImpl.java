package core.basesyntax.report;

import core.basesyntax.storage.Storage;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    private Storage storage;

    public ReportGeneratorImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String getReport() {
        if (storage == null) {
            throw new IllegalArgumentException("Storage can't be null");
        }

        if (storage.getAll().isEmpty()) {
            throw new IllegalArgumentException("Storage is empty");
        }

        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity\n");

        String fruitAndQuantity = storage.getAll().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "," + e.getValue())
                .collect(Collectors.joining("\n"));

        return report
                .append(fruitAndQuantity)
                .toString();
    }
}
