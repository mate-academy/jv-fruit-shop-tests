package core.basesyntax.service.strategy;

public class PurchaseActivityHandler implements ActivityHandler {
    @Override
    public int quantityModify(int quantityBefore, int quantityAfter) {
        if (quantityAfter > quantityBefore) {
            throw new RuntimeException("you don't have enough fruit purchase.");
        }
        if (quantityBefore < 0 || quantityAfter < 0) {
            throw new RuntimeException("Quantity can`t be less than 0");
        }
        return quantityBefore - quantityAfter;
    }
}
