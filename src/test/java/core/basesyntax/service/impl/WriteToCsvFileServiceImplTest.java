package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.ReadFromCsvFileService;
import core.basesyntax.service.WriteToCsvFileService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteToCsvFileServiceImplTest {
    private static final String FIRST_FILENAME = "fruits1.csv";
    private static final String SECOND_FILENAME = "fruits2.csv";
    private static final String THIRD_FILENAME = "fruits3.csv";
    private static final String REPORT_FILENAME = "report.csv";
    private static StringBuilder builder;
    private static CreateReportService reportCreator;
    private static DataConvertService dataConverter;
    private static ReadFromCsvFileService csvReader;
    private static DataProcessService dataProcessor;
    private static WriteToCsvFileService csvWriter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConvertServiceImpl();
        dataProcessor = new DataProcessServiceImpl();
    }

    @BeforeEach
    void setUp() {
        reportCreator = new CreateReportServiceImpl();
        csvReader = new ReadFromCsvFileServiceImpl();
        csvWriter = new WriteToCsvFileServiceImpl();
        builder = new StringBuilder();
    }

    @Test
    void csvWriter_twoFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(FIRST_FILENAME)));

        String expected = reportCreator.createReport();
        csvWriter.write(expected);

        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILENAME))) {
            String line = reader.readLine();

            while (line != null) {
                builder.append(line)
                        .append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file: " + REPORT_FILENAME);
        }
        String actual = builder.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void csvWriter_threeFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(SECOND_FILENAME)));

        String expected = reportCreator.createReport();
        csvWriter.write(expected);

        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILENAME))) {
            String line = reader.readLine();

            while (line != null) {
                builder.append(line)
                        .append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file: " + REPORT_FILENAME);
        }
        String actual = builder.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void csvWriter_fourFruits_Ok() {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(THIRD_FILENAME)));

        String expected = reportCreator.createReport();
        csvWriter.write(expected);

        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILENAME))) {
            String line = reader.readLine();

            while (line != null) {
                builder.append(line)
                        .append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file: " + REPORT_FILENAME);
        }
        String actual = builder.toString().trim();

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}
