package com.journal.newmongoproject.controller;

import com.journal.newmongoproject.entity.JournalEntry;
import com.journal.newmongoproject.entity.User;
import com.journal.newmongoproject.service.JournalEntryService;
import com.journal.newmongoproject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authenticator.getName());
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (!journalEntries.isEmpty() ){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try {
            Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
            journalEntryService.saveEntry(journalEntry, authenticator.getName());
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String userName = authenticator.getName();
        User user = userService.findByUserName(userName);
        Stream<JournalEntry> allEntries = user.getJournalEntries().stream();
        List<JournalEntry> journalEntries = allEntries.filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!journalEntries.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String userName = authenticator.getName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String userName = authenticator.getName();
        User user = userService.findByUserName(userName);
        Stream<JournalEntry> allEntries = user.getJournalEntries().stream();
        List<JournalEntry> journalEntries = allEntries.filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!journalEntries.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null  && !newEntry.getTitle().equals("")  ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
