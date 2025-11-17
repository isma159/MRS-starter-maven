package dk.easv.mrs.GUI.Controller;

// Project Imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;

// Java Imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public TableView<Movie> tableMovies;
    private MovieModel movieModel;

    public Button btnSearch;
    @FXML
    private TableColumn<Movie, Integer> idColumn, yearColumn;
    @FXML
    private TableColumn<Movie, String> titleColumn;


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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("Year"));

        tableMovies.setItems(movieModel.getObservableMovies());



        //tableMovies.setRowFactory();

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

    @FXML
    private void onSearchBtnClick() {

        try {
            movieModel.searchMovie(txtMovieSearch.getText());
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }

    }

    public void updateList() {

        txtMovieSearch.setText("");

        tableMovies.refresh();

        //lstMovies.getItems().clear();

        //lstMovies.setItems(movieModel.getObservableMovies());

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
