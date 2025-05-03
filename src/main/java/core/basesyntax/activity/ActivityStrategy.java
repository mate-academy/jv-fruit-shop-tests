package core.basesyntax.activity;

import core.basesyntax.service.handler.FruitHandler;

public interface ActivityStrategy {
    FruitHandler get(Activities activities);
}
