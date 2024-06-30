package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransitionProcessor;
import core.basesyntax.strategy.NegativeBalanceException;
import core.basesyntax.strategy.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransitionProcessorImplTest {

    private TransitionProcessor transitionProcessor;
    private Map<String, Integer> balanceMap;

    @BeforeEach
    void setUp() {
        transitionProcessor = new TransitionProcessorImpl();
        balanceMap = new HashMap<>();
    }

    @Test
    void processTransaction_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 50);
        transitionProcessor.processTransaction(balanceMap, transaction);
        assertEquals(50, balanceMap.get("apple"));
    }

    @Test
    void processTransaction_supplyOperation_ok() {
        balanceMap.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 30);
        transitionProcessor.processTransaction(balanceMap, transaction);
        assertEquals(50, balanceMap.get("apple"));
    }

    @Test
    void processTransaction_returnOperation_ok() {
        balanceMap.put("banana", 15);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 20);
        transitionProcessor.processTransaction(balanceMap, transaction);
        assertEquals(35, balanceMap.get("banana"));
    }

    @Test
    void processTransaction_purchaseOperation_ok() {
        balanceMap.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 30);
        transitionProcessor.processTransaction(balanceMap, transaction);
        assertEquals(20, balanceMap.get("apple"));
    }

    @Test
    void processTransaction_negativePurchaseOperation_notOk() {
        balanceMap.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 30);
        assertThrows(NegativeBalanceException.class,
                () -> transitionProcessor.processTransaction(balanceMap, transaction));
    }

    @Test
    void processTransaction_invalidOperation_notOk() {
        balanceMap.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(null, "apple", 30);
        assertThrows(NullPointerException.class,
                () -> transitionProcessor.processTransaction(balanceMap, transaction));
    }

    @Test
    void processTransaction_negativeTransactionValue_notOk() {
        balanceMap.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", -30);
        assertThrows(NegativeBalanceException.class,
                () -> transitionProcessor.processTransaction(balanceMap, transaction));
    }
}
