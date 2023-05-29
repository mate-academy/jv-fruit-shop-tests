package core.basesyntax.servicesimpl;

import core.basesyntax.FruitDao;
import core.basesyntax.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.FruitShopSupplier;
import core.basesyntax.strategy.ElementDoesNotExist;
import core.basesyntax.strategy.HandlerConditionFactory;
import java.util.List;
import java.util.Map;

public class FruitShopSupplierImpl implements FruitShopSupplier {
    public static final String COMMA = ",";
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    public static final int HEADER_INDEX = 0;
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final HandlerConditionFactory handler = new HandlerConditionFactory();

    public Map<String, Fruit> fillTheMap(List<String> listWithFruits) {
        for (int i = 0; i < listWithFruits.size(); i++) {
            if (i == HEADER_INDEX) {
                continue;
            }
            if (listWithFruits.get(i) == null) {
                throw new ElementDoesNotExist("Element with index " + i
                        + " is null");
            }
            String[] indexArray = listWithFruits.get(i).split(COMMA);
            handler.getHandler(indexArray[OPERATION_INDEX])
                    .handle(indexArray[FRUIT_INDEX],indexArray[AMOUNT_INDEX]);
        }
        return fruitDao.getAll();
    }
}
