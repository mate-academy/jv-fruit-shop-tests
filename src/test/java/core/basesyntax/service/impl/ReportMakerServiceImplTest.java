package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.ReportMakerException;
import core.basesyntax.service.ReportMakerService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerServiceImplTest {

    private static final String DEFAULT_FILE_PATH = "src/main/resources/default.txt";
    private static final String VALID_TITLE = "fruit,quantity";
    private static final String DEFAULT_EXPECTED =
            VALID_TITLE + System.lineSeparator() + "banana,100"
                    + System.lineSeparator() + "apple,120";
    private ReportMakerService reportMakerService;

    @Before
    public void setUp() {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @After
    public void after() {
        try {
            Files.deleteIfExists(Path.of(DEFAULT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void generateReport_argumentsNull_notOk() {
        try {
            reportMakerService.generateReport(null, null);
        } catch (ReportMakerException e) {
            return;
        }
        fail("generateReport must throw a ReportMakerException if one of the arguments is null");
    }

    @Test
    public void generateReport_firstArgumentNull_notOk() {
        try {
            reportMakerService.generateReport(null, DEFAULT_FILE_PATH);
        } catch (ReportMakerException e) {
            return;
        }
        fail("generateReport must throw a ReportMakerException if one of the arguments is null");
    }

    @Test
    public void generateReport_secondArgumentNull_notOk() {
        try {
            reportMakerService.generateReport(new HashMap<>(), null);
        } catch (ReportMakerException e) {
            return;
        }
        fail("generateReport must throw a ReportMakerException if one of the arguments is null");
    }

    @Test
    public void generateReport_defaultCase_ok() {
        Map<String, Integer> sampleData = new HashMap<>();
        sampleData.put("banana", 100);
        sampleData.put("apple", 120);
        reportMakerService.generateReport(sampleData, DEFAULT_FILE_PATH);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(DEFAULT_FILE_PATH))) {
            String value = br.readLine();
            while (value != null) {
                result.append(value).append(System.lineSeparator());
                value = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Wrong data output", DEFAULT_EXPECTED, result.toString().trim());
    }

    @Test
    public void generateReport_emptyInput_ok() {
        reportMakerService.generateReport(new HashMap<>(), DEFAULT_FILE_PATH);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(DEFAULT_FILE_PATH))) {
            String value = br.readLine();
            while (value != null) {
                result.append(value).append(System.lineSeparator());
                value = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Wrong data output", VALID_TITLE, result.toString().trim());
    }
}
