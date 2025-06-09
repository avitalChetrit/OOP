 
public abstract class Element{
    protected double width;
    protected double length;
    private final String path;

    public Element(double width, double length, String path) {
        this.width = width;
        this.length = length;
        this.path= path==null?"":path;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPath() { return path; }

    public String getFullName() { return path.isEmpty() ? getName() : path + "/" + getName(); }

    public abstract String getName();
    public abstract Habitat getHabitat();
    public abstract void accept(Visitor visitor);
    public abstract double calculateArea();

}

