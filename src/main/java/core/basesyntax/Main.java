package core.basesyntax;

import core.basesyntax.services.FruitService;

public class Main {
    private static final String FIRST_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSVSmallQuantities.csv";
    private static final String FIRST_PATH_RAW_REPORT_NAME = "SampleSourceCSVSmallQuantities";
    private static final String SECOND_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSV2BiggerQuantities.csv";
    private static final String SECOND_PATH_RAW_REPORT_NAME = "SampleSourceCSV2BiggerQuantities";
    private static final String THIRD_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSV3ExtremelyBigQuantities.csv";
    private static final String THIRD_PATH_RAW_REPORT_NAME =
            "SampleSourceCSV3ExtremelyBigQuantities";

    public static void main(String[] args) {
        FruitService.initVars(FIRST_PATH_RAW_REPORT);
        FruitService.processReport(FIRST_PATH_RAW_REPORT_NAME);
        FruitService.initVars(SECOND_PATH_RAW_REPORT);
        FruitService.processReport(SECOND_PATH_RAW_REPORT_NAME);
        FruitService.initVars(THIRD_PATH_RAW_REPORT);
        FruitService.processReport(THIRD_PATH_RAW_REPORT_NAME);
    }
}
