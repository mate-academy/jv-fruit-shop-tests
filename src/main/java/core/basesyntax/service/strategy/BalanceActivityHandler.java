package core.basesyntax.service.strategy;

public class BalanceActivityHandler implements ActivityHandler {
    @Override
    public int quantityModify(int quantityBefore, int quantityAfter) {
        if (quantityBefore < 0 || quantityAfter < 0) {
            throw new RuntimeException("Quantity can`t be less than 0");
        }
        return quantityBefore + quantityAfter;
    }
}
