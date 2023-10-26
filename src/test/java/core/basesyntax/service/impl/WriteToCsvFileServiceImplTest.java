package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.ReadFromCsvFileService;
import core.basesyntax.service.WriteToCsvFileService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    private static final Map<Operation, OperationHandler> operationPicker =
            Map.of(Operation.BALANCE, new BalanceOperationHandlerImpl(),
                    Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                    Operation.RETURN, new ReturnOperationHandlerImpl(),
                    Operation.SUPPLY, new SupplyOperationHandlerImpl());

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConvertServiceImpl();
        dataProcessor = new DataProcessServiceImpl(operationPicker);
    }

    @BeforeEach
    void setUp() {
        reportCreator = new CreateReportServiceImpl();
        csvReader = new ReadFromCsvFileServiceImpl();
        csvWriter = new WriteToCsvFileServiceImpl(REPORT_FILENAME);
        builder = new StringBuilder();
    }

    @ParameterizedTest // idk, can I use it?
    @ValueSource(strings = {
            FIRST_FILENAME,
            SECOND_FILENAME,
            THIRD_FILENAME
    })
    void csvWriter_Fruits_Ok(String filename) {
        dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(filename)));

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
}
