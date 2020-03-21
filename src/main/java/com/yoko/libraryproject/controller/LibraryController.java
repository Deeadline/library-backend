package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.dto.ReserveDto;
import com.yoko.libraryproject.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/library", produces = "application/json", consumes = "application/json")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR')")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping("/availability/{bookId}")
    public ResponseEntity<?> checkAvailability(@PathVariable long bookId) {
        return ResponseEntity.ok().body(service.checkAvailability(bookId));
    }

    @GetMapping("/myBooks")
    public ResponseEntity<?> findAllBooksForUser() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveBook(@RequestBody ReserveDto reserveRequest) {
        service.reserveBook(reserveRequest);
        return ResponseEntity.ok(reserveRequest);
    }

    @DeleteMapping("/returnBook/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable long bookId) {
        service.returnBook(bookId);
        return ResponseEntity.ok(bookId);
    }

    @PutMapping("/commentBook/{bookId}")
    public ResponseEntity<?> commentBook(@PathVariable long bookId, @RequestBody CommentBookDto request) {
        service.comment(bookId, request);
        return ResponseEntity.ok(bookId);
    }
}
