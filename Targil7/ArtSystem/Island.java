/**
 * Represents an aquatic Island element that can contain other elements.
 * Island is a specialization of CompositeElement with a circular shape.
 */
public class Island extends CompositeElement {

    public Island(String name, double diameter, String path) {
        super(diameter, diameter, path, name);
    }

    @Override
    public Habitat getHabitat() {
        return Habitat.AQUATIC;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(width / 2, 2);
    }

    public double getDiameter() {
        return width;
    }

    @Override
    public boolean canContain(Element element) {
        return element.getHabitat() == Habitat.TERRESTRIAL ||
                element.getHabitat() == Habitat.AMPHIBIAN;
    }
}