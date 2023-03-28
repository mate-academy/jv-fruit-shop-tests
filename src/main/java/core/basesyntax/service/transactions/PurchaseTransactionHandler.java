package core.basesyntax.service.transactions;

import core.basesyntax.service.interfaces.strategy.TransactionHandler;

public class PurchaseTransactionHandler implements TransactionHandler {
        @Override
    public Integer getCurrentQuantity(Integer currentQuantity, Integer newQuantity) {
            if(currentQuantity == null|| newQuantity == null){
                throw new RuntimeException("PurchaseTransactionHandler can not handle null");
            }
        return currentQuantity - newQuantity;
    }
}
