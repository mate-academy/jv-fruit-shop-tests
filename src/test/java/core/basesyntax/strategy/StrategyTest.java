package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImplBalance;
import core.basesyntax.dao.FruitDaoImplPurchase;
import core.basesyntax.dao.FruitDaoImplReturn;
import core.basesyntax.dao.FruitDaoImplSupply;
import core.basesyntax.service.InitialisationService;
import org.junit.Assert;
import org.junit.Test;

public class StrategyTest {
    @Test
    public void getImplementationByKey_ok() {
        Strategy<String, FruitDao>
                strategy = new ServiceStrategy<>(InitialisationService.getFruitDaoStrategyMap());
        Assert.assertEquals(FruitDaoImplPurchase.class,
                strategy.getService(InitialisationService.PURCHASE).getClass());
        Assert.assertEquals(FruitDaoImplSupply.class,
                strategy.getService(InitialisationService.SUPPLY).getClass());
        Assert.assertEquals(FruitDaoImplReturn.class,
                strategy.getService(InitialisationService.RETURN).getClass());
        Assert.assertEquals(FruitDaoImplBalance.class,
                strategy.getService(InitialisationService.BALANCE).getClass());
    }
}
