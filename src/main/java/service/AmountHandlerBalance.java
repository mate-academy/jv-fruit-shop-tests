package service;

public class AmountHandlerBalance implements AmountHandler {
    @Override
    public Integer getAmount(Integer productAmount) {
        return Integer.valueOf(productAmount);
    }
}
