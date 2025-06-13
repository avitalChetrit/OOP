import java.util.List;

public class ReportFactory {

    public static Report createReport(String type, String content, List<String> decorators) {
        // create the base report
        Report baseReport = createBaseReport(type, content);

        if (baseReport == null) {
            return null;
        }

        // add the decorators in the chosen order
        Report decoratedReport = baseReport;
        for (String decorator : decorators) {
            decoratedReport = addDecorator(decoratedReport, decorator);
        }

        return decoratedReport;
    }

    private static Report createBaseReport(String type, String content) {
        switch (type) {
            case "1":
                return new IncidentReport(content);
            case "2":
                return new MovementReport(content);
            case "3":
                return new ContactReport(content);
            case "4":
                return new RoutineReport(content);
            default:
                return null;
        }
    }

    private static Report addDecorator(Report report, String decoratorType) {
        switch (decoratorType) {
            case "u":
                return new UrgentReportDecorator(report);
            case "c":
                return new ClassifiedReportDecorator(report);
            case "t":
                return new CommanderTagReportDecorator(report);
            case "a":
                return new AudioAttachmentReportDecorator(report);
            default:
                return report;
        }
    }
}