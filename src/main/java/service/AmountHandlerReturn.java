package service;

public class AmountHandlerReturn implements AmountHandler {
    @Override
    public Integer getAmount(Integer productAmount) {
        return Integer.valueOf(productAmount);
    }
}
