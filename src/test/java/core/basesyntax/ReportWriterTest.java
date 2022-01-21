package core.basesyntax;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.files.ReportWriter;
import core.basesyntax.service.files.ReportWriterImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static final String NON_EXISTENT_FILE = "src/main/resource/non_existent.csv";
    private static final String REPORT_FILE = "src/main/resources/report.csv";
    private static final String REPORT_HEADER = "fruitRecord,quantity";
    private static final List<FruitRecord> DATA_BASE = DataBase.DB;
    private static List<FruitRecord> fruitRecords;
    private static List<String> reportRecords;
    private static ReportWriter reportWriter;

    @Before
    public void setUp() {
        reportWriter = new ReportWriterImpl();
        fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
        reportRecords = new ArrayList<>();
        reportRecords.add("banana,152");
        reportRecords.add("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToNonExistentDirectory_writeReportToFile_Not_OK() {
        DATA_BASE.addAll(fruitRecords);
        reportWriter.writeReportToFile(NON_EXISTENT_FILE, reportRecords);
    }

    @Test
    public void writeReportToExistentDirectory_writeReportToFile_OK() {
        reportWriter.writeReportToFile(REPORT_FILE, reportRecords);
        List<String> result;
        try {
            result = Files.readAllLines(Paths.get(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read input file, " + e);
        }
        reportRecords.add(0,REPORT_HEADER);
        List<String> expected = reportRecords;
        List<String> actual = result;
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        DATA_BASE.clear();
        PrintWriter writer;
        try {
            writer = new PrintWriter(REPORT_FILE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Report file not found, " + e);
        }
        writer.print("");
        writer.close();
    }
}
