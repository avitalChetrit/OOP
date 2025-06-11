public class ClassifiedReportDecorator extends ReportDecorator {

    public ClassifiedReportDecorator(Report report) {
        super(report);
    }

    @Override
    public String getContent() {
        return report.getContent() + " [CLASSIFIED]";
    }
}