package dk.easv.mrs.GUI.Controller;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;

// Java imports
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddViewController {

    private MovieModel movieModel = new MovieModel();
    private MovieViewController movieViewController;

    @FXML
    private TextField txtFieldYear, txtFieldTitle, txtFieldChosenMovie, txtFieldTitleEdit, txtFieldYearEdit, txtFieldChosenMovie2;

    public AddViewController() throws Exception {

    }

    @FXML
    protected void onAddBtnClick() {

        try {
            Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

            if (txtFieldYear.getText().matches("[0-9]+")) {

                movieModel.createMovie(txtFieldTitle.getText(), Integer.parseInt(txtFieldYear.getText()));

                this.movieViewController.btnSearch.fire();

                stage.close();

            }
        } catch (Exception e) {

            displayError(e);

        }

    }

    @FXML
    protected void onUpdateBtnClick() {

        try {
            Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

            Movie chosenMovie = this.movieViewController.tableMovies.getSelectionModel().getSelectedItem();

            if (chosenMovie != null) {

                System.out.println(txtFieldTitleEdit.getText().isEmpty());
                if (txtFieldYearEdit.getText().matches("[0-9]+") && !txtFieldTitleEdit.getText().isBlank()) {

                    Movie movie = new Movie(chosenMovie.getId(), Integer.parseInt(txtFieldYearEdit.getText()), txtFieldTitleEdit.getText());

                    movieModel.updateMovie(movie);

                    this.movieViewController.btnSearch.fire();

                    stage.close();

                }

            }
        } catch (Exception e) {

            displayError(e);

        }
    }

    @FXML
    protected void onSelectionChangedEdit() {

        if (this.movieViewController != null) {

            if (this.movieViewController.tableMovies.getSelectionModel().getSelectedItem() != null) {

                txtFieldChosenMovie.setText(this.movieViewController.tableMovies.getSelectionModel().getSelectedItem().toString());

                txtFieldChosenMovie2.setText(this.movieViewController.tableMovies.getSelectionModel().getSelectedItem().toString());

            }

        }

    }

    @FXML
    protected void onDeleteBtnClick() {

        try {
            Stage stage = (Stage) txtFieldTitle.getScene().getWindow();

            Movie chosenMovie = this.movieViewController.tableMovies.getSelectionModel().getSelectedItem();

            movieModel.deleteMovie(chosenMovie);

            this.movieViewController.btnSearch.fire();

            stage.close();

        } catch (Exception e) {

            displayError(e);

        }

    }

    public void setParent(MovieViewController controller) {

        this.movieViewController = controller;

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

}
