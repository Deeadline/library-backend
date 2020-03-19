package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.AvailableResponse;
import com.yoko.libraryproject.dto.CommentBookDto;
import com.yoko.libraryproject.dto.ReserveDto;
import com.yoko.libraryproject.security.service.UserDetailsImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/api/library", produces = "application/json", consumes = "application/json")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR')")
public class LibraryController {

    @GetMapping("/availability/{bookId}")
    public ResponseEntity<?> checkAvailability(@PathVariable long bookId) {
        return ResponseEntity.ok().body(new AvailableResponse(true));
    }

    @GetMapping("/myBooks")
    public ResponseEntity<?> findAllBooksForUser() {
        UserDetailsImplementation userDetails = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetails.getId());
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveBook(@RequestBody ReserveDto reserveRequest) {
        return ResponseEntity.ok(reserveRequest);
    }

    @DeleteMapping("/returnBook/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookId);
    }

    @PutMapping("/commentBook/{bookId}")
    public ResponseEntity<?> commentBook(@PathVariable long bookId, @RequestBody CommentBookDto request) {
        return ResponseEntity.ok(bookId);
    }
}
