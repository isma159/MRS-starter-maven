package dk.easv.mrs.DAL;

// Project Imports
import dk.easv.mrs.BE.Movie;

// Java Imports
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.*;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final Path filePath = Paths.get(MOVIES_FILE);

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override
    public List<Movie> getAllMovies() throws IOException {

        List<String> lines = Files.readAllLines(filePath);
        List<Movie> movies = new ArrayList<>();

        for (String line: lines) {

            int id = Integer.parseInt(line.split(",")[0]);
            int year = Integer.parseInt(line.split(",")[1]);
            String title = line.split(",")[2];

            movies.add(new Movie(id, year, title));

        }

        return movies;

    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {

        List<String> lines = Files.readAllLines(filePath);
        String lastLine = lines.get(lines.size() - 1);
        int nextId = Integer.parseInt(lastLine.split(",")[0]) + 1;

        String nextLine = nextId + "," + year + "," + title;

        Files.write(Path.of(MOVIES_FILE), (nextLine + "\r\n").getBytes(), APPEND);

        Movie movie = new Movie(nextId, year, title);

        return movie;

    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

        List<String> lines = Files.readAllLines(filePath);

        for (int i = 0; i < lines.size(); i++) {

            String[] separatedLine = lines.get(i).split(",");
            int id = Integer.parseInt(separatedLine[0]);

            if (id == movie.getId()) {

                lines.set(i, movie.getId() + "," + movie.getYear() + "," + movie.getTitle());

            }

        }

        Path tempPathFile = Path.of("_temp/movie_titles_TEMP.txt");
        Files.createFile(tempPathFile);

        for (String line: lines) {

            Files.write(tempPathFile, (line + "\r\n").getBytes(), APPEND);

        }

        Files.copy(tempPathFile, filePath, REPLACE_EXISTING);
        Files.deleteIfExists(tempPathFile);

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

        List<String> lines = Files.readAllLines(filePath);
        int index = 1;

        for (int i = 0; i < lines.size(); i++) {

            int id = Integer.parseInt(lines.get(i).split(",")[0]);

            if (id == movie.getId()) {

                lines.remove(i);
                break;

            }

        }

        for (String line: lines) {

            String[] separatedLine = line.split(",");

            lines.set(lines.indexOf(line), index + "," + separatedLine[1] + "," + separatedLine[2]);

            index++;

        }

        Path tempPathFile = Path.of("_temp/movie_titles_TEMP.txt");
        Files.createFile(tempPathFile);

        for (String line: lines) {

            Files.write(tempPathFile, (line + "\r\n").getBytes(), APPEND);

        }

        Files.copy(tempPathFile, filePath, REPLACE_EXISTING);
        Files.deleteIfExists(tempPathFile);

    }
}