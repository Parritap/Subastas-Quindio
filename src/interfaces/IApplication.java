package interfaces;

import application.App;

public interface IApplication {

    App application = null;

    App getApplication();

    void setApplication(App application);
}
