package core.basesyntax;

import core.basesyntax.db.StockDao;
import core.basesyntax.db.StockDaoStorageImpl;
import core.basesyntax.service.Inventory;
import core.basesyntax.service.InventoryFromCsv;
import core.basesyntax.service.Report;
import core.basesyntax.service.ReportToCsv;

public class Main {
    public static void main(String[] args) {
        final StockDao stockDao = new StockDaoStorageImpl();
        final String inventoryFileName = "inventory.csv";
        final String reportFileName = "report.csv";

        Inventory inventory = new InventoryFromCsv(stockDao, inventoryFileName);
        inventory.synchronizeWithTheStorage();

        Report report = new ReportToCsv(stockDao, reportFileName);
        report.prepare();
    }
}
