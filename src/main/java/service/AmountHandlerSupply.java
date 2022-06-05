package service;

public class AmountHandlerSupply implements AmountHandler {
    @Override
    public Integer getAmount(Integer productAmount) {
        return Integer.valueOf(productAmount);
    }
}
