package appewtc.masterung.rm4it;

/**
 * Created by masterUNG on 4/17/16 AD.
 */
public class Model {

    //Explicit
    private String name;
    private int value; // 0 no checked   1 checked

    public Model(String name, int value) {
        this.name = name;
        this.value = value;
    }   // Constructor

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}   // Model
