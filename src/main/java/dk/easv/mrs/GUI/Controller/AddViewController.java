package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.regex.Pattern;

public class AddViewController {


    private MovieManager movieManager = new MovieManager();

    @FXML
    private TextField txtFieldYear, txtFieldTitle;

    @FXML
    protected void onAddBtnClick() throws Exception {

        if (txtFieldYear.getText().matches("[0-9]+")) {

            Movie movie = movieManager.addRequestedMovie(txtFieldTitle.getText(), Integer.parseInt(txtFieldYear.getText()));

            if (movie != null) {

                System.out.println(":)");

            } else {

                System.out.println(":(");

            }

        }

    }


}
