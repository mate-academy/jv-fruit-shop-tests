package core.basesyntax.service;

import core.basesyntax.service.exceptions.ValidationException;
import core.basesyntax.service.functionalityexpansion.ActivityHandlerProvider;
import core.basesyntax.service.parsefileinfo.FruitTransactionInfo;
import core.basesyntax.service.strategy.ActivityHandler;
import java.util.List;

public class FruitShopService {
    private final ActivityHandlerProvider activityProvider;

    public FruitShopService(ActivityHandlerProvider activityProvider) {
        this.activityProvider = activityProvider;
    }

    public ActivityHandler execute(List<FruitTransactionInfo> fruits) {
        if (fruits.isEmpty()) {
            throw new ValidationException("The list shouldn't be empty");
        }
        ActivityHandler handler = null;
        for (FruitTransactionInfo fruit : fruits) {
            handler = activityProvider.getHandler(fruit.activityType());
            handler.operate(fruit.name(), fruit.quantity());
        }
        return handler;
    }
}
