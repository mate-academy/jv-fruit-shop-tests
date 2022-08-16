package core.basesyntax.service.data;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface DataService<T> {
    List<?> processData(List<String> list);

    public FruitTransaction parser(String row);
}
