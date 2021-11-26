package service.impl;

import dao.FruitDaoImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileWriterService;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static List<String> report;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl(
                new ReportServiceImpl(new FruitDaoImpl()));
        report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("apple,10");
    }

    @Test
    public void write_emptyWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("can`t write in file");
        fileWriterService.write("", report);
    }

    @Test
    public void write_nullWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("the path cannot be null");
        fileWriterService.write(null, report);
    }

    @Test
    public void write_validData_Ok() {
        Assert.assertTrue(fileWriterService.write("src/test/resources/output.csv", report));
        List<String> outputFile;
        try {
            outputFile = Files.readAllLines(Path.of("src/test/resources/output.csv"));
        } catch (IOException e) {
            throw new RuntimeException("can`t read file output.csv");
        }
        Assert.assertEquals(report, outputFile);
    }
}
