package core.basesyntax.service.filehandler;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.EmptyStorageException;
import java.util.Map;

public class ReportGenerator {
    private static final String COMA = ",";
    private static final String HEADER = "fruit,quantity";

    public String generate(Storage storage) {
        if (storage.getData().isEmpty()) {
            throw new EmptyStorageException("Storage shouldn't be empty");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER).append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : storage.getData().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            builder.append(key).append(COMA).append(value).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
