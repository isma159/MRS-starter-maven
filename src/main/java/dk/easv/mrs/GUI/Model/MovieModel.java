package dk.easv.mrs.GUI.Model;

// Project Imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;

// Java Imports
import dk.easv.mrs.BLL.WebhookSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;



public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    private WebhookSender webhookSender = new WebhookSender();

    String state = "";
    String command = "";
    String color = "";

    String jsonPayload = """
            {
                "embeds": [
                    {
                        "title": "%s",
                        "description": "%s",
                        "color": %s
                    }
                ]
            }
            """;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public void createMovie(String title, int year) throws Exception {

        movieManager.addRequestedMovie(title, year);

        Movie movie = new Movie(movieManager.getAllMovies().size(), year, title);

        moviesToBeViewed.add(movie);

        state = "Successful";
        command = "|Added movie| " + movie.toString();
        color = "3066994";

        webhookSender.sendWebhook(jsonPayload.formatted(state,command,color));

    }

    public void updateMovie(Movie movieToBeUpdated) throws Exception {

        movieManager.updateSelectedMovie(movieToBeUpdated);



        int index = moviesToBeViewed.indexOf(movieToBeUpdated);
        moviesToBeViewed.set(index, movieToBeUpdated);

        state = "Successful";
        command = "|Updated movie| " + movieToBeUpdated.toString();
        color = "3066994";

        webhookSender.sendWebhook(jsonPayload.formatted(state,command,color));

    }

    public void deleteMovie(Movie movie) throws Exception {

        if (movie != null) {
            movieManager.deleteSelectedMovie(movie);

            moviesToBeViewed.clear();

            moviesToBeViewed.addAll(movieManager.getAllMovies());

            state = "Successful";
            command = "|Deleted movie| " + movie.toString();
            color = "3066994";

            webhookSender.sendWebhook(jsonPayload.formatted(state,command,color));
        } else {

            state = "Fail";
            command = "Failed to delete movie";
            color = "16711680";

            webhookSender.sendWebhook(jsonPayload.formatted(state,command,color));
        }

    }
}
