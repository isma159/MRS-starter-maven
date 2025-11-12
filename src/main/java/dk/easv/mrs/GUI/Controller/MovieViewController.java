package dk.easv.mrs.GUI.Controller;

// Project Imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
import dk.easv.mrs.GUI.Model.MovieModel;

// Java Imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    private MovieModel movieModel;

    private MovieManager movieManager = new MovieManager();

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    @FXML
    protected void onAddBtnClick() {

        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
            Scene scene = new Scene(root.load(), 322, 289);
            Stage stage = new Stage();

            AddViewController controller = root.getController();
            controller.setParent(this);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

            displayError(e);

        }



    }

    public void updateList() {

        txtMovieSearch.setText("");

        lstMovies.refresh();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
