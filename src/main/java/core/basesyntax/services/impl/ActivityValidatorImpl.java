package core.basesyntax.services.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.FruitDao;
import core.basesyntax.db.dao.FruitDaoStorage;
import core.basesyntax.exceptions.FruitAvailabilityException;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.services.ActivityValidator;

public class ActivityValidatorImpl implements ActivityValidator {
    private FruitDao fruitDao;

    public ActivityValidatorImpl() {
        fruitDao = new FruitDaoStorage();
    }

    @Override
    public boolean validate(Activity activity) {
        if (!activity.getActivityType().equals(ActivityType.BALANCE)
                && !Storage.fruitsStorage.containsKey(activity.getFruit())) {
            throw new FruitAvailabilityException("No "
                    + activity.getFruit().getFruitName()
                    + "s in storage");
        }
        if ((activity.getActivityType().equals(ActivityType.PURCHASE)
                && fruitDao.read(activity.getFruit()) - activity.getQuantity() < 0)) {
            throw new FruitAvailabilityException("Not enough "
                    + activity.getFruit().getFruitName()
                    + "s in storage");
        }

        return true;
    }
}
