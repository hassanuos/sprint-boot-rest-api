package com.journal.newmongoproject.service;

import com.journal.newmongoproject.entity.JournalEntry;
import com.journal.newmongoproject.entity.User;
import com.journal.newmongoproject.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) throws Exception {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new Exception("An error occurred while saving the entry ", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        JournalEntry saved = journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        try {
            boolean removed = false;
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }catch (Exception e){
            log.error("Something went wrong ", e);
            throw new RuntimeException("An error occurred while delete by id " + e);
        }
    }
}
