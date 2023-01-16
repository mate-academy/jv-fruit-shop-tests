package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import org.junit.AfterClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    @Test
    public void transactionProcessor_ok() {

    }

    @AfterClass
    public void tearDown() {
        FruitDao.storage.clear();
    }

}