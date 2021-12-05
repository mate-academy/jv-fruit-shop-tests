package core.basesyntax;

import dao.ShopOperationsDao;
import dao.ShopOperationsDaoCsvImpl;

public class Main {
    public static void main(String[] args) {
        ShopOperationsDao shopOperationsDao = new ShopOperationsDaoCsvImpl("fruit-shop.csv",
                "fruit-shop-report.csv");
        shopOperationsDao.generateReport();
    }
}
