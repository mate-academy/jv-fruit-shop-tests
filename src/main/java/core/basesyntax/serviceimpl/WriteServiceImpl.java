package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.WriteService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteServiceImpl implements WriteService {

    @Override
    public void writeReport(String reportPath, String reportText) {
        if (reportPath == null || reportText == null) {
            throw new InvalidDataException("Write report don't take null values");
        }
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(reportPath))) {
            bufferedWriter.write(reportText);
        } catch (IOException e) {
            throw new InvalidDataException("Can't write this file: " + reportPath);
        }
    }
}
