package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.TransactionStorage;
import core.basesyntax.service.file.WriteDataToStorageService;
import org.junit.After;
import org.junit.Test;

public class WriteDataToStorageImplTest {
    private WriteDataToStorageService writeDataToStorageService = new WriteDataToStorageImpl();
    private TransactionStorage transactionStorage = new TransactionStorage();

    @Test
    public void writeData_firstLine_Ok() {
        String goodDataLine = "type,fruit,quantity";
        writeDataToStorageService.writeData(goodDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test
    public void writeData_goodData_Ok() {
        String goodDataLine = "b,banana,20";
        writeDataToStorageService.writeData(goodDataLine);
        assertEquals(1, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_emptyData_notOk() {
        writeDataToStorageService.writeData("");
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_badDataOperation_notOk() {
        String badDataLine = "v,banana,20";
        writeDataToStorageService.writeData(badDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_badQualityType_notOk() {
        String badDataLine = "b,banana,ten";
        writeDataToStorageService.writeData(badDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_noSeparatorContains_notOk() {
        String badDataLine = "bbanana50";
        writeDataToStorageService.writeData(badDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_toManySeparatorContains_notOk() {
        String badDataLine = "v,ba,nana,50";
        writeDataToStorageService.writeData(badDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_onlySeparator_notOk() {
        String badDataLine = ",,,,,,,,";
        writeDataToStorageService.writeData(badDataLine);
        assertEquals(0, transactionStorage.getAll().size());
    }

    @After
    public void afterEachTest() {
        transactionStorage.getAll().clear();
    }
}
