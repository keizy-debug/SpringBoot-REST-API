package net.artnet.securitySeries.service;


import lombok.RequiredArgsConstructor;
import net.artnet.securitySeries.model.*;
import net.artnet.securitySeries.model.request.AuthorCreationRequest;
import net.artnet.securitySeries.model.request.BookCreationRequest;
import net.artnet.securitySeries.model.request.BookLendRequest;
import net.artnet.securitySeries.model.request.MemberCreationRequest;
import net.artnet.securitySeries.repo.AuthorRepo;
import net.artnet.securitySeries.repo.BookRepo;
import net.artnet.securitySeries.repo.LendRepo;
import net.artnet.securitySeries.repo.MemberRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final LendRepo  lendRepo;
    private final MemberRepo memberRepo;

    public Book readBook(Long id){
        Optional<Book> book = bookRepo.findById(id);
        if(book.isPresent()){
            return book.get();
        }

        throw new EntityNotFoundException("cant find the book under the given ID");
    }

    public List<Book> readBooks(String isbn){
        return bookRepo.findAll();
    }

    public Book readBook(String isbn){
        Optional<Book> book= bookRepo.findByIsbn(isbn);
        if(book.isPresent()){
            return book.get();
        }

        throw new EntityNotFoundException("cant book under given ISBM");
    }

    public Book createBook(BookCreationRequest book){
        Optional<Author> author= authorRepo.findById(book.getAuthorId());
        if(!author.isPresent()){
            throw new EntityNotFoundException("Author not found");
        }

        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepo.save(bookToCreate);
    }

    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }

    public Member createMember(MemberCreationRequest request){
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        return memberRepo.save(member);
    }

    public Member updateMember(Long id, MemberCreationRequest request){
        Optional<Member> optionalMember = memberRepo.findById(id);
        if(!optionalMember.isPresent()){
            throw new EntityNotFoundException("Member not present in the database");
        }

        Member member =optionalMember.get();
        member.setFirstName(request.getLastName());
        member.setLastName(request.getFirstName());
        return memberRepo.save(member);
    }

    public Author createAuthor(AuthorCreationRequest request){
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepo.save(author);
    }

    public List<String> lendABook(List<BookLendRequest> list) {
        List<String> bookApprovedToBurrow = new ArrayList<>();
        list.forEach(bookLendRequest -> {
            Optional<Book> bookForId = bookRepo.findById(bookLendRequest.getBookId());
            if (!bookForId.isPresent()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }

            Optional<Member> memberForId = memberRepo.findById(bookLendRequest.getMemberId());
            if (!memberForId.isPresent()) {
                throw new EntityNotFoundException("Member not found in the database");
            }

            Member member = memberForId.get();
            if (member.getStatus() != MemberStatus.ACTIVE) {
                throw new RuntimeException("Member is not active to proceed a lending");
            }

            Optional<Lend> burrowedBook = lendRepo.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);
            if (!burrowedBook.isPresent()) {
                bookApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
              //  lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepo.save(lend);
            }
        });
        return bookApprovedToBurrow;
    }
}
