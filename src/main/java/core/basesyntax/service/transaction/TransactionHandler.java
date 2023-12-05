package core.basesyntax.service.transaction;

public interface TransactionHandler {
    int perform(int balance, int quantity);
}
