package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessingService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TransactionProcessingServiceImplTest {
    private final TransactionProcessingService transactionProcessing;
    private final List<FruitTransaction> valid;

    public TransactionProcessingServiceImplTest() {
        transactionProcessing = new TransactionProcessingServiceImpl();
        valid = new ArrayList<>();
        valid.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10));
        valid.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 3));
        valid.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 8));
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void accept_valid_Ok() {
        transactionProcessing.accept(valid);
        Assert.assertEquals(Integer.valueOf(15), Storage.storage.get("banana"));
    }

    @Test
    public void accept_empty_Ok() {
        transactionProcessing.accept(Collections.emptyList());
        Assert.assertTrue(Storage.storage.isEmpty());
    }

    @Test (expected = NullDataException.class)
    public void accept_null_NotOk() {
        transactionProcessing.accept(null);
    }
}
