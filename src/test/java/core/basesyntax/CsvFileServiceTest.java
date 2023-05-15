package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileServiceTest {
    private static final String INPUT_VALID_FILE = "src/test/resources/input.csv";
    private static final String REPORT_VALID_FILE = "src/test/resources/report.csv";
    private static final String INPUT_WRONG_FILE = "src/test/resources/absend.csv";
    private static final String INPUT_NULL_FILE = "src/test/resources/null.csv";
    private static final String REPORT_WRONG_FILE = "src/test/reources/report$*!.csv";

    private static final List<String> validInputTransactions = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50",
            "s,apple,150",
            "b,lemon,200",
            "b,strawberry,250",
            "s,lemon,200",
            "p,strawberry,250",
            "r,strawberry,250",
            "p,strawberry,300",
            "s,strawberry,300");
    private static final List<String> validReportStrings = List.of(
            "fruit,quantity",
            "banana,152",
            "apple,240",
            "lemon,400",
            "strawberry,250");

    private static FileReaderService fileReaderService;
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
        fileWriterService = new FileWriterServiceImpl();
        File file = new File(REPORT_VALID_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void readFromFile_transaction_ok() {
        List<String> transactionsFromFile = fileReaderService.readFromFile(INPUT_VALID_FILE);
        Assert.assertEquals(String.format("Incorrect value read from file - %s\n",
                INPUT_VALID_FILE), validInputTransactions, transactionsFromFile);
    }

    @Test
    public void readFromFile_null_ok() {
        List<String> transactionsFromFile = fileReaderService.readFromFile(INPUT_NULL_FILE);
        Assert.assertEquals(String.format("Incorrect value read from file - %s\n",
                INPUT_NULL_FILE), new ArrayList<String>(), transactionsFromFile);
    }

    @Test
    public void readFromFile_isAbsent_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.readFromFile(INPUT_WRONG_FILE);
        });
    }

    @Test
    public void writeToFile_report_ok() {
        fileWriterService.writeToFile(validReportStrings, REPORT_VALID_FILE);
        List<String> actualWrittenToFile;
        File file = new File(REPORT_VALID_FILE);
        try {
            actualWrittenToFile = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("Written to file report is not equals ",
                validReportStrings, actualWrittenToFile);
    }

    @Test
    public void writeToFile_wrongName_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeToFile(new ArrayList<>(), REPORT_WRONG_FILE);
        });
    }

}
