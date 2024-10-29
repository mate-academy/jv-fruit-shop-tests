package core.basesyntax.report;

import core.basesyntax.service.ShopService;

public interface ReportGenerator {
    String generateReport();

    void setShopService(ShopService shopService);
}
