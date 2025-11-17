package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.db.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess{


    public MovieDAO_DB() throws IOException {}

    private DBConnector dbConnector = new DBConnector();

    @Override
    public List<Movie> getAllMovies() throws Exception {

        List<Movie> allMovies = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection()) {

            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM dbo.Movie";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                int Id = rs.getInt("Id");
                String Title = rs.getString("Title");
                int Year = rs.getInt("Year");

                Movie movie = new Movie(Id, Year, Title);
                allMovies.add(movie);

            }

        }
        catch (SQLException e) {

            throw new Exception("Failed to get movies from database.", e);

        }

        return allMovies;

    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO dbo.Movie (Title, Year) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setInt(2, year);
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {

                id = rs.getInt(1);

            }

            conn.close();

            return new Movie(id, year, title);

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add movie to database.", e);

        }
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("UPDATE dbo.Movie SET Title = ?, Year = ? WHERE Id = ?");
            ps.setString(1, movie.getTitle()); ps.setInt(2, movie.getYear()); ps.setInt(3, movie.getId());
            ps.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update movie.", e);

        }

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("DELETE FROM dbo.Movie WHERE Id = ?");
            ps.setInt(1, movie.getId());
            ps.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete movie from database.", e);

        }

    }
}
