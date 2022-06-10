package core.basesyntax.service.impl;

import core.basesyntax.dao.ProductStorageDao;
import core.basesyntax.dao.ProductStorageDaoImpl;
import core.basesyntax.db.ProductStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private final String reportHeader = "fruit,quantity";
    private final String newLine = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        ProductStorageDao productStorageDao = new ProductStorageDaoImpl();
        reportService = new ReportServiceImpl(productStorageDao);
    }

    @Test
    public void create_withNotEmptyStorage_ok() {
        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder expected = new StringBuilder();
        expected.append(reportHeader).append(newLine);
        expected.append("banana,").append(100).append(newLine);
        expected.append("apple,").append(50).append(newLine);
        expected.append("mango,").append(150);

        ProductStorage.products.put("banana", 100);
        ProductStorage.products.put("apple", 50);
        ProductStorage.products.put("mango", 150);
        String actual = reportService.create();
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void create_withEmptyStorage_ok() {
        String actual = reportService.create();
        Assert.assertEquals(reportHeader + newLine, actual);
    }

    @After
    public void tearDown() {
        ProductStorage.products.clear();
    }
}
