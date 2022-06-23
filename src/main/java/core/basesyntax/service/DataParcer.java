package core.basesyntax.service;

import core.basesyntax.fruit.FruitTransaction;
import java.util.List;

public interface DataParcer {
    List<FruitTransaction> getFruitsMoving(List<String> list);
}
