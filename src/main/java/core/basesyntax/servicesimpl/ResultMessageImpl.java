package core.basesyntax.servicesimpl;

import core.basesyntax.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.ResultMessage;
import core.basesyntax.strategy.ElementDoesNotExist;
import java.util.Map;

public class ResultMessageImpl implements ResultMessage {
    public static final String COMMA = ",";
    public static final String TITLE_TO_FILE = "fruit,quantity";

    public String makeMessage(FruitDaoImpl fruitDaoImpl) {
        StringBuilder sb = new StringBuilder(TITLE_TO_FILE + System.lineSeparator());
        for (Map.Entry<String, Fruit> entry: fruitDaoImpl.getAll().entrySet()) {
            if (entry.getValue() == null) {
                throw new ElementDoesNotExist("The value of Map is null");
            }
            sb.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue().getQuantityLeft())
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
