package service;

import java.io.File;

public interface FruitShopService {
    File generateDailyReport(File inputFile, File reportFile);
}
