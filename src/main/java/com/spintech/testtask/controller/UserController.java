package com.spintech.testtask.controller;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.facade.UserFacade;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity registerUser(final @RequestParam String email,
                                       final @RequestParam String password) {
        return prepareResponseEntity(userService.registerUser(email, password) != null);
    }

    @PostMapping("/actors/{actorId}")
    public ResponseEntity addFavouriteActor(final @RequestParam String email,
                                            final @RequestParam String password,
                                            final @PathVariable String actorId) {
        return prepareResponseEntity(userFacade.addFavouriteActor(email, password, actorId));
    }

    @DeleteMapping("/actors/{actorId}")
    public ResponseEntity deleteFavouriteActor(final @RequestParam String email,
                                               final @RequestParam String password,
                                               final @PathVariable String actorId) {
        return prepareResponseEntity(userFacade.deleteFavouriteActor(email, password, actorId));
    }

    @PostMapping("/movies/{movieId}")
    public ResponseEntity addWatchedMovie(final @RequestParam String email,
                                            final @RequestParam String password,
                                            final @PathVariable String movieId) {
        return prepareResponseEntity(userFacade.addWatchedMovie(email, password, movieId));
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity deleteWatchedMovie(final @RequestParam String email,
                                             final @RequestParam String password,
                                             final @PathVariable String movieId) {
        return prepareResponseEntity(userFacade.deleteWatchedMovie(email, password, movieId));
    }

    @GetMapping("/unwatched/movies")
    public ResponseEntity getUnwatchedMoviesWithFavouriteActors(final @RequestParam String email,
                                                                final @RequestParam String password){
        final User user = userService.findUser(email, password);
        if(isNull(user)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getUnwatchedMoviesWithFavouriteActors(user));
    }

    private ResponseEntity prepareResponseEntity(final boolean isSuccess) {
        if(isSuccess) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
