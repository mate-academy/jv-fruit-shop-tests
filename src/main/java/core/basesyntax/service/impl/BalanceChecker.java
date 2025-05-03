package core.basesyntax.service.impl;

public class BalanceChecker {
    void checkBalance(int balance, String fruit) {
        if (balance < 0) {
            throw new RuntimeException("Balance is negative, please check your file for fruit: "
            + fruit);
        }
    }
}
