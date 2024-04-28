package core.basesyntax.service.report;

import core.basesyntax.db.FruitStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITER = ",";
    private FruitStorage storage;

    public ReportServiceImpl(FruitStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<String> createReport() {
        if (storage.getAll().equals(null)) {
            throw new RuntimeException("Value cannot be null");
        }
        if (storage.getAll().equals(Map.of())) {
            throw new RuntimeException("The storage is empty");
        }
        List<String> result = new ArrayList<>();
        result.add(HEADER);
        for (Map.Entry<String, Integer> entry : storage.getAll().entrySet()) {
            result.add(entry.getKey() + DELIMITER + entry.getValue());
        }
        return result;
    }
}
