/**
 * Represents a Flag element with specific attributes like color and carrier height.
 * A Flag is a rectangular element belonging to a terrestrial habitat.
 */
public class Flag extends Element {
    Color color;
    int carrierHeight;

    public Flag(double width, double length, Color color, int carrierHeight, String path) {
        super(width, length, path);
        this.color = color;
        this.carrierHeight = carrierHeight;
    }

    @Override
    public String getName() {
        return "flag";
    }

    @Override
    public Habitat getHabitat() {
        return Habitat.TERRESTRIAL;
    }

    public Color getColor() {
        return color;
    }

    public int getCarrierHeight() {
        return carrierHeight;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double calculateArea() {
        return width * length; // Rectangle
    }


}