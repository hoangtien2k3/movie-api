package com.hoangtien2k3.movieapi.service.movie;

import com.hoangtien2k3.movieapi.dto.MovieDto;
import com.hoangtien2k3.movieapi.dto.MoviePageResponse;
import com.hoangtien2k3.movieapi.entity.Movie;
import com.hoangtien2k3.movieapi.exceptions.payload.FileExistsException;
import com.hoangtien2k3.movieapi.exceptions.payload.MovieNotFoundException;
import com.hoangtien2k3.movieapi.mapper.MovieMapper;
import com.hoangtien2k3.movieapi.repository.MovieRepository;
import com.hoangtien2k3.movieapi.service.FileService;
import com.hoangtien2k3.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws Exception {
        /** 1. upload the file **/
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists, Please choose a different filename");
        }
        String uploadFileName = fileService.uploadFile(path, file);

        /** 2. set the value of field 'poster' as fileName **/
        movieDto.setPoster(uploadFileName);
        ;

        /** 3. map dto to Movie object **/
        Movie movie = MovieMapper.map(movieDto);

        /** 4. save the movie object -> save the movie object **/
        Movie saveMovie = movieRepository.save(movie);

        /** 5. generate the posterUrl **/
        String posterUrl = baseUrl + "/file/" + uploadFileName;

        /** 6. map Movie object to Dto object and return it **/
        return MovieMapper.map(saveMovie, posterUrl);
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        /** 1. check the data in db and if exists, fetch the data of give ID **/
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));

        /** 2. generate posterUrl **/
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        /** 3. map to MovieDto object and return it **/
        return MovieMapper.map(movie, posterUrl);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        /** 1. fetch all data from db **/
        /** 2. iterate through the list, generate posterUrl for each movie object
         and map to movieDto object
         **/
        return movieRepository.findAll()
                .stream()
                .map(movie -> {
                    String posterUrl = baseUrl + "/file/" + movie.getPoster();
                    return MovieMapper.map(movie, posterUrl);
                })
                .toList();
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws Exception {
        /** 1. check if movie object exists with give movieId **/
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));

        /** 2. if file is null, do not thing
         if file is not null, there delete exists file associate with the record
         and update the new file
         **/
        String fileName = mv.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        /** 3. set movieDto's post value, according to step 2**/
        movieDto.setPoster(fileName);

        /** 4. map to it movie object **/
        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirectory(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        /** 5. save the movie object -> return the movie object **/
        Movie saveMovie = movieRepository.save(movie);

        /** 6. generate postUrl for it **/
        String posterUrl = baseUrl + "/file/" + fileName;

        /** 7. map to the movieDto object, return it **/
        return MovieMapper.map(saveMovie, posterUrl);
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // 1. check if movie object exits in DB
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));
        Integer id = mv.getMovieId();

        // 2. delete the file associate with the object
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));

        // 3. delete the movie object
        movieRepository.delete(mv);

        return "Movie delete with id: " + id;
    }

    @Override
    public MoviePageResponse getAllMovieWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return getMoviePageResponse(pageNumber, pageSize, pageable);
    }

    @Override
    public MoviePageResponse getAllMovieWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                                 String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return getMoviePageResponse(pageNumber, pageSize, pageable);
    }

    private MoviePageResponse getMoviePageResponse(Integer pageNumber, Integer pageSize, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);

        List<MovieDto> movieDtos = moviePage.getContent()
                .stream()
                .map(movieDto -> {
                    String posterUrl = baseUrl + "/file/" + movieDto.getPoster();
                    return MovieMapper.map(movieDto, posterUrl);
                })
                .toList();

        return new MoviePageResponse(
                movieDtos,
                pageNumber,
                pageSize,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.isLast());
    }
}
