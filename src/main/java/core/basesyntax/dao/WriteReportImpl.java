package core.basesyntax.dao;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.services.ValidatorService;
import core.basesyntax.services.ValidatorServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteReportImpl implements WriteReport {
    private final ValidatorService validatorService = new ValidatorServiceImpl();

    @Override
    public void write(String report, String fileName) {
        validatorService.nullInputDataValidator(fileName);
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(fileName))) {
            writeToFile.write(report);
        } catch (IOException e) {
            throw new ValidationException("Can't write to file");
        }
    }
}
