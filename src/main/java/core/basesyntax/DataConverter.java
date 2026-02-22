package core.basesyntax;

import java.util.List;

public interface DataConverter {
    public List<FruitTransaction> convert(List<String> lines);
}
