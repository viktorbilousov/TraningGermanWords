package AppBilder;

import java.net.URL;

public interface Model {
    void init(URL pathToFXML);
    void updateData();
    void updateDataParent();
    void show();
    void close();

}
