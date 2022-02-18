package core.basesyntax.db;

import java.util.ArrayList;
import java.util.List;

public class ReportStorage {
    private static final List<String> storage = new ArrayList<>();

    public void add(String processLine) {
        storage.add(processLine);
    }

    public List<String> getAll() {
        return storage;
    }
}
