package core.basesyntax.service.storage;

import java.util.List;

public interface StoreService {
    boolean applyToDb(List<String> dataFromFile);

    String getDbReport();
}
