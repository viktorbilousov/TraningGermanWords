package AppBilder.util.tablecell;



public class EditingCellSwitcher extends EditingCell {

    public EditingCellSwitcher() {
    }

    @Override
    public void commitEdit(Object newValue) {
        String line = newValue.toString();
        if(line.toLowerCase().equals("def")) {
            oldText = newValue.toString();
            super.commitEdit(newValue);
            return;
        }
        try{
            Integer.parseInt(line);
            oldText = line;
            super.commitEdit(newValue);
        }catch (NumberFormatException e){
            super.commitEdit(oldText);
        }
    }


}

