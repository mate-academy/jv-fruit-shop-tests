package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerTest {
    private ReportMaker reportMaker;
    private File file;

    @Before
    public void setUp() {
        reportMaker = new ReportMaker();
        file = new File("file");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void makeReport_forEmpty_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportMaker.makeReport(file),"Should get exception for empty");
    }

    @Test
    public void makeReport_InvalidFirstLine_notOk() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            fileWriter.write("aSd,das,dasd\n");
            fileWriter.write("b,banana,23\n");
            fileWriter.write("p,banana,13");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class, () -> reportMaker.makeReport(file),
                "Should get exception inappropriate 1 line");
    }

    @Test
    public void makeReport_invalidOperation_notOk() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            fileWriter.write("type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "n,banana,13\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class, () -> reportMaker.makeReport(file),
                "Should get exception for inappropriate operation");
    }

    @Test
    public void makeReport_negativeFruitNumber_notOk() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            fileWriter.write("type,fruit,quantity\n");
            fileWriter.write("b,banana,23\n");
            fileWriter.write("p,banana,33");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class, () -> reportMaker.makeReport(file));
    }

    @Test
    public void makeReport_validData_Ok() {
        File expected = new File("expected");
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                BufferedWriter expectedWriter = new BufferedWriter(new FileWriter(expected))) {
            fileWriter.write("type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13\n"
                    + "r,apple,10\n"
                    + "p,apple,20\n"
                    + "p,banana,5\n"
                    + "s,banana,50");
            expectedWriter.write("fruit,quantity\n"
                    + "banana,152\n"
                    + "apple,90");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(Files.readAllLines(expected.toPath()),
                    Files.readAllLines(reportMaker.makeReport(file).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        file.delete();
    }
}
