package core.basesyntax.shopoperations;

public class Balance implements ShopBalanceOperation {
    @Override
    public int calculate(int previousValue, int value) {
        return previousValue + value;
    }
}
