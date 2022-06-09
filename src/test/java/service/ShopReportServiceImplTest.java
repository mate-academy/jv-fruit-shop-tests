package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import java.util.List;
import model.ProductAccount;
import org.junit.Assert;
import org.junit.Test;

public class ShopReportServiceImplTest {

    @Test
    public void testGetShopBalanceReport() {

        Storage memdb = new Storage();
        memdb.products.clear();
        ProductAccountDaoImpl dao = new ProductAccountDaoImpl(memdb);
        ShopReportServiceImpl shopReportService = new ShopReportServiceImpl(dao);
        ProductAccount product0 = new ProductAccount("ReportProduct0").setAmount(13);
        dao.add(product0);
        List<String> reportList = shopReportService.getShopBalanceReport();
        String repHeaders = (HeaderParts.FRUIT.name().toLowerCase()
                + "," + HeaderParts.QUANTITY.name().toLowerCase());
        Assert.assertEquals("Returned report header are wrong",repHeaders,reportList.get(0));
        Assert.assertEquals("Returned report row is wrong","ReportProduct0,13",reportList.get(1));
    }

}

