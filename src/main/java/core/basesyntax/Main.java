package core.basesyntax;

import java.util.List;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FormReport;
import core.basesyntax.service.InputDataValidator;
import core.basesyntax.service.Parse;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FormReportImpl;
import core.basesyntax.service.impl.InputDataValidatorImpl;
import core.basesyntax.service.impl.ParseImpl;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/res/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/res/report.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> strings = fileReaderService.readFile(INPUT_FILE_NAME);

        InputDataValidator validator = new InputDataValidatorImpl();
        validator.chekDate(strings);

        Parse parse = new ParseImpl();
        parse.parseList(strings);

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        FormReport formReport = new FormReportImpl();
        fileWriterService.writeToFile(formReport.reportFromStorage(), OUTPUT_FILE_NAME);
    }
}
