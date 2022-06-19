package strategy;

import dao.FruitDao;
import model.FruitTransaction;
import service.OperationHandler;

public class SubtractOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public SubtractOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void doTransaction(FruitTransaction transaction) {
        int availableQuantity = fruitDao.get(transaction.getFruit()) != null
                ? fruitDao.get(transaction.getFruit()) : 0;
        if (availableQuantity >= transaction.getQuantity()) {
            fruitDao.add(transaction.getFruit(), availableQuantity - transaction.getQuantity());
            return;
        }
        throw new RuntimeException("you have no fruit's quantity available for PURCHASE operation."
                + " You need " + transaction.getQuantity() + " but you have " + availableQuantity);
    }
}
