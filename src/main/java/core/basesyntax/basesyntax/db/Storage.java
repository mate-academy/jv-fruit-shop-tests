package core.basesyntax.basesyntax.db;

import java.util.Map;

public interface Storage {
    int getQuantity(String fruit);

    void setQuantity(String fruit, int quantity);

    Map<String, Integer> getAll();

    void clear();
}
