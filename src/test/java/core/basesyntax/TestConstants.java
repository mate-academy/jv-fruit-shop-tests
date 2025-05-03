package core.basesyntax;

import java.util.List;

public class TestConstants {
    public static final String REPORT_HEADER = "fruit,quantity" + System.lineSeparator();
    public static final String REPORT_FILE_PATH_TO_WRITE = "src/test/resources/finalReport.csv";
    public static final String WRONG_PATH = "src/test/resources/fi/reportToWrite.csv";
    public static final int DEFAULT_QUANTITY = 10;
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final int ZERO_QUANTITY = 0;
    public static final String TITLE = "type,fruit,quantity";
    public static final List<String> REPORT = List.of(TITLE, "b,banana,20,", "b,apple,100",
            "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50");
    public static final int NEGATIVE_QUANTITY = -1;
    public static final int PART_OF_DEFAULT_QUANTITY = 1;
}
