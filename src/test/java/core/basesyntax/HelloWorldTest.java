package core.basesyntax;

import core.basesyntax.fortests.FileReaderTest;
import core.basesyntax.fortests.FileReaderTestImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final String FILE1 = "src/main/resources/file1.csv";
    private static final String FILE2 = "src/main/resources/file2.csv";
    private static final String FILE3 = "src/main/resources/file3.csv";
    private static final String FILE4 = "src/main/resources/file4.csv";
    private static final String FILE5 = "src/main/resources/file5.csv";
    private static final String FILE6 = "src/main/resources/file6.csv";
    private static final String FILE7 = "src/main/resources/file7.csv";
    private static final String EXPECTED_RESULT = "src/main/resources/expected.csv";
    private static final String FINAL_REPORT = "src/main/resources/finalReport.csv";

    private HelloWorld helloWorld = new HelloWorldImpl();

    @Test
    void transfer_FileNotExists_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport("FILE9");
                });
    }

    @Test
    void transfer_EmptyFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE2);
                });
    }

    @Test
    void transfer_FieldFruitIsNull_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE3);
                });
    }

    @Test
    void transfer_AmountIsLessThanZero_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE4);
                });
    }

    @Test
    void transfer_BalanceEqualsZero_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE5);
                });
    }

    @Test
    void transfer_OperationIsNull_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE6);
                });
    }

    @Test
    void transfer_AmountIsLessThanZero_NotNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE7);
                });
    }

    @Test
    void transfer_ValidData_Ok() {
        helloWorld.getFileOfReport(FILE1);
        FileReaderTest fileReaderTest = new FileReaderTestImpl();
        String expected = fileReaderTest.readFile(EXPECTED_RESULT);
        FileReaderTest fileReaderTest1 = new FileReaderTestImpl();
        String actual = fileReaderTest1.readFile(FINAL_REPORT);
        Assert.assertEquals(actual, expected);
    }
}
