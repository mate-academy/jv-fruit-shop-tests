package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;

public class PurchaseOperationHandler implements OperationHandler {
    private final FruitStorageDao fruitDao;

    public PurchaseOperationHandler(FruitStorageDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(TransactionDto transactionDto) {
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        int newQuantity = fruitDao.get(fruit) - transactionDto.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("There are not so many fruits available, "
                    + fruitDao.get(fruit) + " is stored, you want to buy "
                    + transactionDto.getQuantity());
        }
        fruitDao.add(fruit, newQuantity);
    }
}
