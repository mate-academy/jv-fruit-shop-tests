package core.basesyntax.data.servise.ipl;

import core.basesyntax.data.model.FruitTransaction;
import core.basesyntax.data.servise.ShopService;
import core.basesyntax.data.servise.StrategyOperationService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final StrategyOperationService strategyOperationService;

    public ShopServiceImpl(StrategyOperationService strategyOperationService) {
        this.strategyOperationService = strategyOperationService;
    }

    @Override
    public void process(List<FruitTransaction> operations) {
        for (FruitTransaction operation : operations) {
            strategyOperationService.get(operation.getOperation()).handle(operation);
        }
    }
}
