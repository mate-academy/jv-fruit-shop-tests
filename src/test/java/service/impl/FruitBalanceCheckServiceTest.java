package service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitBalanceCheckServiceTest {
    @Test
    void checkNegativeBalanceExpectException_notOk() {
        FruitBalanceCheckService fruitBalanceCheckService = new FruitBalanceCheckService();

        Assertions.assertThrows(RuntimeException.class, ()
                -> fruitBalanceCheckService.checkNegativeBalance(-4, "banana"));
    }
}
