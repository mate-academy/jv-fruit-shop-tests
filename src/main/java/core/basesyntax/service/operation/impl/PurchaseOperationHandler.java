package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.operation.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private FruitDao fruitDao;

    public PurchaseOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(String[] record) {
        String fruitName = record[1];
        int quantity = Integer.parseInt(record[2]);
        if (!(fruitName.equals("apple") || fruitName.equals("banana"))) {
            throw new RuntimeException("Invalid fruit " + fruitName);
        }
        int newQuantity = fruitDao.get(fruitName).getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new RuntimeException("Not enough " + fruitName);
        }
        fruitDao.update(fruitName, newQuantity);
    }
}
