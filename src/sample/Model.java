package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private StringProperty text = new SimpleStringProperty();

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.setValue(text);
    }

    public StringProperty textProperty(){
        return text;
    }
}
