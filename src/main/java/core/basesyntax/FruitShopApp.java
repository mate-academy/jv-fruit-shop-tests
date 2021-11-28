package core.basesyntax;

import core.basesyntax.service.impl.FruitShopServiceImpl;
import java.io.File;

public class FruitShopApp {
    private static final String FILE_SEPARATOR = File.separator;
    private static final String RESOURCES_PATH = "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR;
    private static final String CSV_PATH = RESOURCES_PATH + "CSV.csv";
    private static final String WRONG_QUANTITY_CSV = RESOURCES_PATH + "WRONG_QUANTITY_CSV.csv";
    private static final String WRONG_SUMMARY_VALUE_CSV = RESOURCES_PATH + "WRONG_SUMMARY_VALUE_CSV.csv";

    public static void main(String[] args) {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        System.out.println(fruitShopService.getReport(CSV_PATH));
        System.out.println(fruitShopService.getReport(WRONG_SUMMARY_VALUE_CSV));
        System.out.println(fruitShopService.getReport(WRONG_QUANTITY_CSV));
    }
}
