/**
 * Represents a Lake, which is a composite element capable of containing other elements.
 * A Lake has a circular shape defined by its diameter. It specializes in housing
 */
public class Lake extends CompositeElement {
    public Lake(String name, double diameter, String path) {
        super(diameter, diameter, path, name);
    }

    @Override
    public Habitat getHabitat() {
        return Habitat.TERRESTRIAL;
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
}