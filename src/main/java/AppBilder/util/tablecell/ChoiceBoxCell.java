package AppBilder.util.tablecell;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;


public class ChoiceBoxCell<T, S> extends TableCell<T, S> {

   private ChoiceBox<S> choiceBox = new ChoiceBox<>();

    public ChoiceBox<S> getChoiceBox() {
        return choiceBox;
    }

    @Override
    protected void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setGraphic(null);
            return;
        }
        setGraphic(choiceBox);
    }

    private void processEdit(S value) {
        commitEdit(value);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    @Override
    public void commitEdit(S newValue) {
        super.commitEdit(newValue);
        // ((Item) this.getTableRow().getItem()).setName(value);
        setGraphic(null);
    }



}