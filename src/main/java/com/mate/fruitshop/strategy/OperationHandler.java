package com.mate.fruitshop.strategy;

import com.mate.fruitshop.model.Transaction;

public interface OperationHandler {
    void process(Transaction transaction);
}
