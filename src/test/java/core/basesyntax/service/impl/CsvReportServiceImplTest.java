package core.basesyntax.service.impl;


import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationProcessingStrategy;
import core.basesyntax.service.OperationProcessor;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.TransactionsHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CsvReportServiceImplTest {
    StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void getReport_Ok() {
        storageDao.update("Lemon",100);
        ReportService reportService = new CsvReportServiceImpl(storageDao);
        String actual = reportService.createReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("Lemon,100");
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}