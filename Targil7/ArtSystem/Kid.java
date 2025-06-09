/**
 * Represents a Kid as an Element with specific attributes such as birth year and hair color.
 * A Kid is part of an amphibian habitat, combining rectangular and circular shapes
 * to define its structure. It extends the base Element class, providing child-specific
 * characteristics and behaviors.
 */
public class Kid extends Element {
    private final int birthYear;
    private final Color hairColor;

    public Kid(double width, double height, int birthYear, Color hairColor, String path) {
        super(width, height, path);
        this.birthYear = birthYear;
        this.hairColor = hairColor;
    }

    @Override
    public String getName() {
        return "kid";
    }

    @Override
    public Habitat getHabitat() {
        return Habitat.AMPHIBIAN;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double calculateArea() {
        // Rectangle + Circle: width * (length - width) + π * (width/2)²
        return width * (length - width) + Math.PI * Math.pow(width / 2, 2);
    }

    public int getBirthYear() {
        return birthYear;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public int getAge() {
        return 2025 - birthYear;
    }
}