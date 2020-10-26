package com.github.navaell.investmentportfolio.controller;

import com.github.navaell.investmentportfolio.model.Account;
import com.github.navaell.investmentportfolio.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    // GET method to fetch all phones
    @GetMapping("/phones")
    public List<Account> getAllPhones() {
        return accountRepository.findAll();
    }

    // GET method to fetch phone by Id
    @GetMapping("/phones/{id}")
    public ResponseEntity<Account> getById(@PathVariable(value = "id") UUID accountId)
            throws Exception {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account " + accountId + " not found"));
        return ResponseEntity.ok().body(account);
    }

    // POST method to create a phone
    @PostMapping("/phones")
    public Account createPhone(@Valid @RequestBody Account account) {
        return accountRepository.save(account);
    }

    // PUT method to update a phone's details
    @PutMapping("/phones/{id}")
    public ResponseEntity<Account> updatePhone(
            @PathVariable(value="id") UUID phoneId, @Valid @RequestBody Account phoneDetails
    ) throws Exception {
        Account account = accountRepository.findById(phoneId)
                .orElseThrow(() -> new Exception("Phone " + phoneId + " not found"));

        account.setfName(phoneDetails.getfName());
        account.setlName(phoneDetails.getlName());

        final Account updatedPhone = accountRepository.save(account);
        return ResponseEntity.ok(updatedPhone);
    }

    // DELETE method to delete a phone
    @DeleteMapping("/phone/{id}")
    public Map<String, Boolean> deletePhone(@PathVariable(value="id") UUID phoneId) throws Exception {
        Account account = accountRepository.findById(phoneId)
                .orElseThrow(() -> new Exception("Phone " + phoneId + " not found"));

        accountRepository.delete(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}