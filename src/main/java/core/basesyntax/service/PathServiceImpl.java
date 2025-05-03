package core.basesyntax.service;

import java.io.File;

public class PathServiceImpl implements PathService {

    public static final String INPUT_FILENAME_SUFFIX = " input file.csv";
    public static final String REPORT_FILENAME_SUFFIX = " report file.csv";
    public static final String REPORTS_FOLDER_PATH = getReportsFolderPath();

    //поскольку этого файла еще не существует то этот метод возвращает только полный путь
    //где его должен будет создать fileService.createNewFile(reportFullPath);
    @Override
    public String getReportFullPath(String dateOfReport) {
        if (dateOfReport == null) {
            //return null;//якщо не викинути exception а просто поверн null то тест повинен зафейл
            throw new RuntimeException("String dateOfReport cannot be null!");
        }
        if (dateOfReport.isEmpty()) {
            throw new RuntimeException("String dateOfReport cannot be empty!");
        }
        String reportFileName = dateOfReport + REPORT_FILENAME_SUFFIX;
        return REPORTS_FOLDER_PATH + File.separator + reportFileName;
    }

    @Override
    public String getInputFullPath(String dateOfInput) {
        if (dateOfInput == null) {
            //return null;//якщо не викинути exception а просто поверн null то тест повинен зафейл
            throw new RuntimeException("String dateOfReport cannot be null!");
        }
        if (dateOfInput.isEmpty()) {
            throw new RuntimeException("String dateOfReport cannot be empty!");
        }
        String inputFileName = dateOfInput + INPUT_FILENAME_SUFFIX;
        return REPORTS_FOLDER_PATH + File.separator + inputFileName;
    }

    private static String getReportsFolderPath() {
        String projectPath = System.getProperty("user.dir");
        return projectPath + File.separator + "reports";
    }

}
