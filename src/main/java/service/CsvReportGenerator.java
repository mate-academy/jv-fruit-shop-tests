package service;

import db.Storage;
import java.util.Map;

public class CsvReportGenerator implements Generator {
  private static final String SEPARATOR = ",";
  private static final String REPORT_HEADER = "fruit,quantity";
  private static final String LINE_BREAK = System.lineSeparator();

  @Override
  public String generateReport() {
    StringBuilder report = new StringBuilder();
    report.append(REPORT_HEADER).append(LINE_BREAK);

    for (Map.Entry<String, Integer> fruit : Storage.fruitsStore.entrySet()) {
      report.append(fruit.getKey()).append(SEPARATOR).append(fruit.getValue()).append(LINE_BREAK);
    }
    return report.toString();
  }
}
