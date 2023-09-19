package strategy.impl;

import strategy.ActivityTypeHandler;

public class PurchaseTypeHandler implements ActivityTypeHandler {
    @Override
    public int getNewQuantity(int before, int after) {
        return before - after;
    }
}
