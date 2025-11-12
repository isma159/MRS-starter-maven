package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
import dk.easv.mrs.BLL.WebhookSender;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddViewController {

    private MovieModel movieModel = new MovieModel();
    private MovieViewController movieViewController;

    @FXML
    private TextField txtFieldYear, txtFieldTitle, txtFieldChosenMovie, txtFieldTitleEdit, txtFieldYearEdit, txtFieldChosenMovie2;

    public AddViewController() throws Exception {
    }

    @FXML
    protected void onAddBtnClick() throws Exception {

        Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

        if (txtFieldYear.getText().matches("[0-9]+")) {

            movieModel.createMovie(txtFieldTitle.getText(), Integer.parseInt(txtFieldYear.getText()));

            this.movieViewController.updateList();

            stage.close();

        }

    }

    @FXML
    protected void onUpdateBtnClick() throws Exception {

        Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

        Movie chosenMovie = this.movieViewController.lstMovies.getSelectionModel().getSelectedItem();

        if (chosenMovie != null) {

            System.out.println(txtFieldTitleEdit.getText().isEmpty());
            if (txtFieldYearEdit.getText().matches("[0-9]+") && !txtFieldTitleEdit.getText().isBlank()) {

                Movie movie = new Movie(chosenMovie.getId(), Integer.parseInt(txtFieldYearEdit.getText()), txtFieldTitleEdit.getText());

                movieModel.updateMovie(movie);

                this.movieViewController.updateList();

                stage.close();

            }

        }
    }

    @FXML
    protected void onSelectionChangedEdit() {

        if (this.movieViewController != null) {

            if (this.movieViewController.lstMovies.getSelectionModel().getSelectedItem() != null) {

                txtFieldChosenMovie.setText(this.movieViewController.lstMovies.getSelectionModel().getSelectedItem().toString());

                txtFieldChosenMovie2.setText(this.movieViewController.lstMovies.getSelectionModel().getSelectedItem().toString());

            }

        }

    }

    @FXML
    protected void onDeleteBtnClick() throws Exception {

        Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

        Movie chosenMovie = this.movieViewController.lstMovies.getSelectionModel().getSelectedItem();

        movieModel.deleteMovie(chosenMovie);

        this.movieViewController.updateList();


        stage.close();
    }

    public void setParent(MovieViewController controller) {

        this.movieViewController = controller;

    }

}
