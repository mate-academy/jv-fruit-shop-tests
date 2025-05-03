package core.basesyntax.service.convert;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ConvertData {
    List<FruitTransaction> convert(List<String> data);
}
