package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import java.util.List;
import model.ProductAccount;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ShopReportServiceImplTest {

    @Test
    void getShopBalanceReport() {

        Storage memdb = new Storage();
        memdb.products.clear();
        ProductAccountDaoImpl dao = new ProductAccountDaoImpl(memdb);
        ShopReportServiceImpl shopReportService = new ShopReportServiceImpl(dao);
        ProductAccount product0 = new ProductAccount("ReportProduct0").setAmount(13);
        dao.add(product0);
        List<String> reportList = shopReportService.getShopBalanceReport();
        String repHeaders = (HeaderParts.FRUIT.name().toLowerCase()
                + "," + HeaderParts.QUANTITY.name().toLowerCase());
        Assert.assertEquals("",repHeaders,reportList.get(0));
        Assert.assertEquals("","ReportProduct0,13",reportList.get(1));
    }
}

