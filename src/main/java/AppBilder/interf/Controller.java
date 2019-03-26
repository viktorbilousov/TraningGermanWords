package AppBilder.interf;

import AppBilder.Model;
import javafx.application.Application;

public interface Controller {
    void initialize();
    void updateElementsData();
    void setMyModel(Model model);
}
