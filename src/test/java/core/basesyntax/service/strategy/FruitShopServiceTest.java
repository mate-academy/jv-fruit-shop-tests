package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private FruitShopService fruitShopService;
    private List<FruitTransaction> processedTransactions;

    @BeforeEach
    void setUp() {
        fruitShopService = new TestFruitShopService();
        processedTransactions = new ArrayList<>();
    }

    @Test
    void processOfOperations_ValidTransactions_SuccessfullyProcessesTransactions() {
        List<FruitTransaction> transactions = createTransactions();

        fruitShopService.processOfOperations(transactions);

        assertEquals(transactions, processedTransactions);
    }

    private List<FruitTransaction> createTransactions() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("apple", 10));
        transactions.add(createTransaction("banana", 5));
        return transactions;
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    private class TestFruitShopService implements FruitShopService {
        @Override
        public void processOfOperations(List<FruitTransaction> fruitTransactions) {
            processedTransactions.addAll(fruitTransactions);
        }
    }
}
