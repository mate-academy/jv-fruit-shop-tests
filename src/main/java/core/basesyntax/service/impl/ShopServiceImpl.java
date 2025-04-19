package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.service.ShopService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    
    private final OperationStrategy operationStrategy;
    
    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }
    
    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("transactions can't be a null");
        }
        
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler =
                    operationStrategy.getOperationHandler(transaction.getOperation());
            
            handler.updateNumberOfFruit(transaction);
        }
    }
}
