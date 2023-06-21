package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionException;
import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    private static final String OUTPUT_FILE_PATH = "src/main/resources/output.csv";
    private static final String EXCEPTION_MESSAGE_FOR_WRITING = "Can't write report to file: ";
    private static final String EXCEPTION_MESSAGE_FOR_NULL_PATH = "Path isn't correct: ";
    private static final String EXCEPTION_MESSAGE_FOR_INCORRECT_PATH
            = "Can't write report to file. FilePath is: ";
    private static final String EXCEPTION_MESSAGE_FOR_NULL_CONTENT
            = "Can't write report to file. Report is: ";

    @Override
    public void write(String report, String filePath) {
        if (filePath == null) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_INCORRECT_PATH + filePath);
        }
        if (!filePath.equals(OUTPUT_FILE_PATH)) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NULL_PATH + filePath);
        }
        if (report == null) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NULL_CONTENT + report);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_WRITING + filePath);
        }
    }
}
