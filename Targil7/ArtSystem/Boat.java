// Boat class
public class Boat extends Element {
    Material material;

    public Boat(double width, double length, Material m, String path) {
        super(width, length, path);
        this.material = m;
    }

    @Override
    public String getName() {
        return "boat";
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
        // Rectangle + Half circle: width * (length - width / 2) + π * (width/2)²/2
        return width * (length - width / 2) + Math.PI * Math.pow(width / 2, 2) / 2;
    }

    public Material getMaterial() {
        return material;
    }
}


