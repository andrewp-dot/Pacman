package view.fields;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Field;

interface FieldView {
    public void displayFiled();
    public Rectangle getFieldView(Field field);
}
