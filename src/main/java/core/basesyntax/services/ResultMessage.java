package core.basesyntax.services;

import core.basesyntax.FruitDaoImpl;

public interface ResultMessage {
    String makeMessage(FruitDaoImpl fruitDao);
}
