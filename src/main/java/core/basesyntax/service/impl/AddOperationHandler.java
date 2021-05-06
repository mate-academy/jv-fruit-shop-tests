package core.basesyntax.service.impl;

import core.basesyntax.model.dao.FruitDao;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.interfaces.OperationHandler;

public class AddOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public AddOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public int apply(FruitRecordDto fruitRecordDto) {
        int currentQuantity = fruitDao.getCurrentQuantity(fruitRecordDto);
        int newQuantity;
        try {
            newQuantity = Math.addExact(currentQuantity, fruitRecordDto.getQuantity());
        } catch (ArithmeticException e) {
            throw new RuntimeException("Quantity can't be larger than " + Integer.MAX_VALUE, e);
        }
        fruitDao.update(fruitRecordDto, newQuantity);
        return newQuantity;
    }
}
