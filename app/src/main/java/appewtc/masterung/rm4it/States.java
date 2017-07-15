package appewtc.masterung.rm4it;

public class States {


    String name = null;
    boolean selected = false;

    public States(String name, boolean selected) {
        super();

        this.name = name;
        this.selected = selected;
    }	// Constructor



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}