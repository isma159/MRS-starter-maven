package dk.easv.mrs.BLL;

// Project Imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.util.MovieSearcher;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.MovieDAO_DB;
import dk.easv.mrs.DAL.MovieDAO_File;
import dk.easv.mrs.DAL.MovieDAO_Mock;
import dk.easv.mrs.GUI.Controller.AddViewController;
import dk.easv.mrs.GUI.Controller.MovieViewController;

// Java Imports
import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;

    public MovieManager() throws Exception {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    public Movie addRequestedMovie(String title, int year) throws Exception {

        return movieDAO.createMovie(title, year);

    }

    public void updateSelectedMovie(Movie movie) throws Exception {

        movieDAO.updateMovie(movie);

    }

    public void deleteSelectedMovie(Movie movie) throws Exception {

        movieDAO.deleteMovie(movie);

    }


}
