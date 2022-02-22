package net.artnet.securitySeries.controller;


import lombok.RequiredArgsConstructor;
import net.artnet.securitySeries.model.Book;
import net.artnet.securitySeries.model.Member;
import net.artnet.securitySeries.model.request.BookCreationRequest;
import net.artnet.securitySeries.model.request.MemberCreationRequest;
import net.artnet.securitySeries.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/book")
    public ResponseEntity readBooks(@RequestParam(required = false) String isbn){
        if(isbn == null){
            return ResponseEntity.ok(libraryService.readBooks(null));
        }

        return ResponseEntity.ok(libraryService.readBooks(isbn));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> readBook(@PathVariable Long bookId){
        return ResponseEntity.ok(libraryService.readBook(bookId));
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody BookCreationRequest request){
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId){
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member")
    public ResponseEntity<Member> createMember (@RequestBody MemberCreationRequest request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    @PatchMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember (@RequestBody MemberCreationRequest request, @PathVariable Long memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }
}
