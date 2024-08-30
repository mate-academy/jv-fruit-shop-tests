package util;

public final class TestConstants {
    //Fruits names
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final String ORANGE = "orange";
    //Reports data lines
    public static final String HEADER = "type,fruit,quantity";
    public static final String REPORT_LINE_1 = "b,banana,20";
    public static final String REPORT_LINE_2 = "b,apple,100";
    public static final String REPORT_LINE_3 = "s,banana,100";
    public static final String REPORT_LINE_4 = "p,banana,13";
    public static final String REPORT_LINE_5 = "r,apple,4";
    public static final String REPORT_HEADER = "fruit,quantity";
    //Wrong reports data lines
    public static final String REPORT_LINE_INVALID_OPERATION = "DOG,banana,13";
    public static final String INVALID_REPORT_LINE_LENGTH = "b,20";
    public static final String REPORT_LINE_NULL_FRUIT = "b,null,100";
    public static final String REPORT_LINE_NULL_OPERATION = "null,banana,100";
    public static final String REPORT_LINE_NULL_QUANTITY = "p,banana,null";
    public static final String REPORT_LINE_NEGATIVE_QUANTITY = "s,banana,-100";
    //Operations codes
    public static final String BALANCE_OPERATION_COD = "b";
    public static final String PURCHASE_OPERATION_COD = "p";
    public static final String SUPPLY_OPERATION_COD = "s";
    //Wrong values
    public static final String EMPTY_VALUE = "";
    public static final String NULL_FRUIT_NAME = "null";
    public static final String NEGATIVE_QUANTITY = "-100";
    //Quantities
    public static final String QUANTITY_20 = "20";
    public static final String QUANTITY_35 = "35";
    public static final String WRONG_NUMBER_FORMAT = "juice";
    //Balance data for report
    public static final String BANANA_BALANCE = "banana,20";
    public static final String APPLE_BALANCE = "apple,100";
    //Paths to files
    public static final String RIGHT_PATH_TO_READ = "src/test/resources/validReportToRead.csv";
    public static final String WRONG_PATH_TO_READ = "src/test/resources/ToRead.csv";
    public static final String RIGHT_PATH_TO_WRITE = "src/test/resources/validReportToWrite.csv";

    private TestConstants() {
        throw new UnsupportedOperationException("Can't instantiate constants class");
    }
}
