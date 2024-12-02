module project.kamussearchengine {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.kamussearchengine to javafx.fxml;
    exports project.kamussearchengine;
    exports project.kamussearchengine.Controllers;
    opens project.kamussearchengine.Controllers to javafx.fxml;
}