package core.basesyntax.service.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;

public class RemoveOperationStrategy implements RecordHandler {
    private static final int DEFAULT_VALUE = 0;
    private static final String EXCEPTION_MESSAGE = "Amount of fruits you want "
            + "to buy is bigger than we currently have";
    private static final String LESS_THAN_ZERO = "Quantity can't be less than 0";
    private final FruitDao fruitDao;

    public RemoveOperationStrategy(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public long applyAction(FruitRecordDto fruitRecordDto) {
        Fruit fruit = new Fruit(fruitRecordDto.getFruit().getName());
        if (fruitRecordDto.getQuantity() < 0) {
            throw new RuntimeException(LESS_THAN_ZERO);
        }
        int currentQuantity = fruitDao.getQuantity(fruit).orElse(DEFAULT_VALUE);
        int subtractionResult = currentQuantity - fruitRecordDto.getQuantity();
        if (subtractionResult < DEFAULT_VALUE) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        fruitDao.save(fruit, subtractionResult);
        return subtractionResult;
    }
}
