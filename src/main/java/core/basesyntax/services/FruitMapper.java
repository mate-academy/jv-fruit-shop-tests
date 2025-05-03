package core.basesyntax.services;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitMapper {
    List<FruitTransaction> mapData(List<String> data);
}
