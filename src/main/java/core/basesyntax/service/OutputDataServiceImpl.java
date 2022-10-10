package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputDataServiceImpl implements OutputDataService {
    private final FruitDao fruitDao;

    public OutputDataServiceImpl() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public String toStringConverter() {
        return fruitDao.getStorageData().entrySet()
                .stream()
                .map(this::getFromMap)
                .collect(Collectors.joining());
    }

    private String getFromMap(Map.Entry<String, Integer> set) {
        if (set.getValue() == null) {
            throw new NullPointerException("Can't get data from the storage, because "
                    + set.getKey() + " has \"null\" value");
        }
        String fruitName = set.getKey();
        String quantity = set.getValue().toString();
        return fruitName + "," + quantity + System.lineSeparator();
    }
}
