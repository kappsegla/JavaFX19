package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private StringProperty text = new SimpleStringProperty();

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.setValue(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    private BooleanProperty enabled = new SimpleBooleanProperty(true);

    public boolean isEnabled() {
        return enabled.get();
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    private ObservableList<String> items = FXCollections.observableArrayList(
            "A", "B", "C", "D", "E");

    public ObservableList<String> getItems() {
        return items;
    }

    private StringProperty selectedItem = new SimpleStringProperty();

    public String getSelectedItem() {
        return selectedItem.get();
    }

    public StringProperty selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem.set(selectedItem);
    }

    IntegerProperty size = new SimpleIntegerProperty();

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }
}
