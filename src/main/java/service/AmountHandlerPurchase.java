package service;

public class AmountHandlerPurchase implements AmountHandler {
    @Override
    public Integer getAmount(Integer productAmount) {
        return Integer.valueOf(-productAmount);
    }
}
