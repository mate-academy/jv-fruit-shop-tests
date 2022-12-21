package core.basesyntax.service.activity;

import core.basesyntax.model.FruitTransaction;

public interface ActivityHandler {
    boolean handle(FruitTransaction fruitTransaction);
}
