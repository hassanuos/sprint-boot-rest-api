package com.journal.newmongoproject.controller;

import com.journal.newmongoproject.api.response.WeatherResponse;
import com.journal.newmongoproject.entity.JournalEntry;
import com.journal.newmongoproject.entity.User;
import com.journal.newmongoproject.repository.UserRepository;
import com.journal.newmongoproject.service.UserService;
import com.journal.newmongoproject.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateEntry(@RequestBody User user){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String userName = authenticator.getName();
        User userInDB = userService.findByUserName(userName);
        if (userInDB != null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveNewUser(userInDB);

            return new ResponseEntity<>(userInDB, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteJournalEntryById(){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteUserBy(authenticator.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeatherData();
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<User> users = userService.getAllEntries();
//        if (!users.isEmpty() ){
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
