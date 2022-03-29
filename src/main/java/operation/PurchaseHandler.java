package operation;

import dao.FruitShopDao;
import dao.FruitShopDaoImpl;
import model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public OperationHandler handle(FruitTransaction fruitTransaction) {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        if (fruitShopDao.getValue(fruitTransaction) == null) {
            return null;
        }
        fruitTransaction.setQuantity(fruitShopDao.getValue(fruitTransaction)
                - fruitTransaction.getQuantity());
        fruitShopDao.save(fruitTransaction);
        return null;
    }
}
