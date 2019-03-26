package AppBilder.util.tablecell;


import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell<T, S> extends TableCell<T, S> {
    final private Button button = new Button("X");

    public Button getButton() {
        return button;
    }

    @Override
    protected void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setGraphic(null);
            return;
        }
        setGraphic(button);
    }
}
