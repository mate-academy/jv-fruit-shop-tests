package core.basesyntax.servise.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface Converter {
    List<FruitTransaction> convert(List<String> input);
}
