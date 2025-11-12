package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
import dk.easv.mrs.BLL.WebhookSender;
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


    private MovieManager movieManager = new MovieManager();
    private MovieViewController movieViewController;
    private WebhookSender webhookSender = new WebhookSender();

    @FXML
    private TextField txtFieldYear, txtFieldTitle, txtFieldChosenMovie, txtFieldTitleEdit, txtFieldYearEdit, txtFieldChosenMovie2;

    @FXML
    protected void onAddBtnClick() throws Exception {

        Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

        if (txtFieldYear.getText().matches("[0-9]+")) {

            Movie movie = movieManager.addRequestedMovie(txtFieldTitle.getText(), Integer.parseInt(txtFieldYear.getText()));

            this.movieViewController.updateList();

            stage.close();

            if (movie != null) {

                String command = "Successfully Added Movie " + txtFieldTitle.getText() + "(" + txtFieldYear.getText() + ").";

                webhookSender.sendWebhook(command);

            } else {

                String command = "Task Failed.";

                webhookSender.sendWebhook(command);

            }

        } else {

            System.out.println("Invalid Year");

        }



    }

    @FXML
    protected void onUpdateBtnClick() throws Exception {

        Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

        Movie chosenMovie = this.movieViewController.lstMovies.getSelectionModel().getSelectedItem();

        if (chosenMovie != null) {

            System.out.println("chosen movie found");

            System.out.println(txtFieldTitleEdit.getText().isEmpty());
            if (txtFieldYearEdit.getText().matches("[0-9]+") && !txtFieldTitleEdit.getText().isBlank()) {

                System.out.println("Valid inputs");

                String command = "Successfully updated movie from (" + chosenMovie.toString() + ") to (" + chosenMovie.getId() + ": " + txtFieldTitleEdit.getText() + " (" + Integer.parseInt(txtFieldYearEdit.getText()) + ")).";

                webhookSender.sendWebhook(command);

                Movie movie = new Movie(chosenMovie.getId(), Integer.parseInt(txtFieldYearEdit.getText()), txtFieldTitleEdit.getText());

                movieManager.updateSelectedMovie(movie);

                this.movieViewController.updateList();

                stage.close();

            } else {

                System.out.println("no valid inputs :(");

            }

        } else {

            System.out.println("no chosen movie");

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

        if (chosenMovie != null) {

            String command = "Successfully deleted movie " + chosenMovie + ".";

            webhookSender.sendWebhook(command);

            movieManager.deleteSelectedMovie(chosenMovie);

            this.movieViewController.updateList();

            stage.close();
        }

    }

    public void setParent(MovieViewController controller) {

        this.movieViewController = controller;

    }

}
