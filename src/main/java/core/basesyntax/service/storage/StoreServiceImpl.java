package core.basesyntax.service.storage;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class StoreServiceImpl implements StoreService {
    private static OperationStrategy operationStrategy;
    private static FruitsDao fruitsDao;
    private static final String TITLE = "fruit,quantity";
    private static final String CSV_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    public StoreServiceImpl(OperationStrategy operationStrategy, FruitsDao fruitsDao) {
        this.operationStrategy = operationStrategy;
        this.fruitsDao = fruitsDao;
    }

    @Override
    public boolean applyToDb(List<String> dataFromFile) {
        for (int i = 0; i < dataFromFile.size(); i++) {
            String[] data = dataFromFile.get(i).split(CSV_SEPARATOR);
            Fruit operationFruit =
                    new Fruit(Fruit.OperationType.get(data[OPERATION_INDEX]),
                            data[FRUIT_INDEX],
                            Integer.parseInt(data[VALUE_INDEX]));

            int oldValue = fruitsDao.get(operationFruit);

            int value = Integer.parseInt(data[VALUE_INDEX]);

            String operationType = String.valueOf(Fruit.OperationType.get(data[OPERATION_INDEX]));
            if (!Fruit.contains(operationType)) {
                throw new RuntimeException("Incorrect operation " + operationType);
            }
            int newValue = operationStrategy
                    .get(Fruit.OperationType.valueOf(operationType))
                    .calculateValue(oldValue, value);

            fruitsDao.save(operationFruit, newValue);
        }
        return true;
    }

    @Override
    public String getDbReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE);
        Map<Fruit, Integer> operationMap = fruitsDao.getAll();
        for (Map.Entry<Fruit, Integer> entry : operationMap.entrySet()) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(entry.getKey().getFruitName()
                    + CSV_SEPARATOR + entry.getValue());
        }
        return stringBuilder.toString();
    }
}
