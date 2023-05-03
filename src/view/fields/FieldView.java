package view.fields;
import game.fields.EndField;
import game.fields.PathField;
import game.fields.StartField;
import game.fields.WallField;
import javafx.scene.shape.Rectangle;
import game.common.Field;

public class FieldView {
    public final class FieldType {
        public static final String WALL = "wallField";
        public static final String PATH = "pathField";
        public static final String START = "startField";
        public static final String END = "endField";;
    }
    public static Rectangle getFieldView(Field field)
    {
        Rectangle fieldView = new Rectangle();
        if(field instanceof WallField) fieldView.setId(FieldType.WALL);
        else if (field instanceof PathField) {
            if (field instanceof StartField) fieldView.setId(FieldType.START);
            else if (field instanceof EndField) fieldView.setId(FieldType.END);
            else fieldView.setId(FieldType.PATH);
        }
        return fieldView;
    }
}
