import java.util.ArrayList;
import java.util.List;

/**
 * Composite interface for elements that can contain others
  */
public abstract class CompositeElement extends Element {
    private final String name;
    private List<Element> children;

    public CompositeElement(double width, double length, String path, String name) {
        super(width, length, path);
        this.name = name;
        this.children = new ArrayList<>();

    }

    public List<Element> getChildren() {
        return children;
    }

    public String getName() { return name; }

    public void addChild(Element element) {
        if (canContain(element)) {
            children.add(element);
        } else {
            System.out.println(getName() + " cannot contain " + element.getName());
        }
    }

    public boolean canContain(Element element) {
        return element.getHabitat() == this.getHabitat() ||
                element.getHabitat() == Habitat.AMPHIBIAN;
    }


}