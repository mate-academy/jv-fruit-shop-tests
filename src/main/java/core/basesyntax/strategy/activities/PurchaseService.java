package core.basesyntax.strategy.activities;

import core.basesyntax.db.dao.FruitDao;
import core.basesyntax.db.dao.FruitDaoStorage;
import core.basesyntax.model.Activity;

public class PurchaseService implements ActivityService {
    private FruitDao fruitDao;

    public PurchaseService() {
        this.fruitDao = new FruitDaoStorage();
    }

    @Override
    public void apply(Activity activity) {
        if (fruitDao.read(activity.getFruit()) - activity.getQuantity() < 0) {
            throw new RuntimeException("Not enough "
                    + activity.getFruit().getFruitName()
                    + " in storage");
        }
        fruitDao.update(activity.getFruit(),
                fruitDao.read(activity.getFruit()) - activity.getQuantity());
    }
}
