package core.basesyntax.services;

import core.basesyntax.dao.FruitDaoImpl;

public interface ResultMessage {
    String makeMessage(FruitDaoImpl fruitDao);
}
