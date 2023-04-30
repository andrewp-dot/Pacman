package view.fields;
import javafx.scene.control.Button;

import java.lang.reflect.Field;

interface FieldView {
    public void displayFiled();
    public Button getFieldView(Field field);

}
