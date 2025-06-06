public class ContactReport implements Report {
    private final String content;
    public ContactReport(String content) { this.content = content; }

    @Override
    public String getContent() {
        return getType() + ": " + content;
    }

    @Override
    public String getType() { return "ContactReport"; }

}
