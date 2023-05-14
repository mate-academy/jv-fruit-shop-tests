package core.basesyntax.service;

import core.basesyntax.service.activities.Handler;
import core.basesyntax.strategy.StoreStrategy;
import core.basesyntax.strategy.StoreStrategyImpl;
import java.util.Map;

public class StorageServiceImpl implements StorageService {
    private static final int FIRST_CELL_NUMBER = 0;
    private CsvFileReader reader;

    public StorageServiceImpl(CsvFileReader reader) {
        this.reader = reader;
    }

    @Override
    public void putDataToStorage(Map<String, Handler> map) {
        FileValidator validator = new FileValidatorImpl();
        StoreStrategy strategy = new StoreStrategyImpl(map);
        for (String[] value : reader.getDataFromFile()) {
            validator.validate(value);
            strategy.get(value[FIRST_CELL_NUMBER]).createFruitObject(value);
        }
    }
}
