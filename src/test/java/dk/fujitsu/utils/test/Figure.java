package dk.fujitsu.utils.test;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class Figure {
    private int id;
    private Dimension dimension;
    private List<Shape> shapes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
}
