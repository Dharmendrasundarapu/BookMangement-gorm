package example.micronaut.gorm.service

import example.micronaut.gorm.domain.Author
import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.model.AuthorModel
import example.micronaut.gorm.model.Book
import grails.gorm.transactions.Transactional
import org.springframework.dao.DataIntegrityViolationException

import javax.inject.Singleton

@Singleton
class BookService {

    @Transactional
    def saveAuthor(Book book) {
//        if (book == null || book.author == null) {
//            return "Invalid data or author data provided"
//        }
//
//        // Find or create the author
//        Author author = findOrCreateAuthor(book.author)
//
//        // Create and populate BookEntity
//        BookEntity bookEntity = new BookEntity()
//        bookEntity.title = book.title
//        bookEntity.pages = book.pages
//        bookEntity.pubdate = book.pubdate
//        bookEntity.author = author // Set the author field
//
//        // Save the BookEntity
//        if (!bookEntity.save(flush: true)) {
//            bookEntity.errors.allErrors.each { println it }
//            return "Error saving book"
//        }
//
//        return "Book saved successfully"
        BookEntity bookEntity = new BookEntity()
        bookEntity.title = book.title
        bookEntity.pubdate = book.pubdate
        bookEntity.pages = book.pages
        AuthorModel authorModel = new AuthorModel()
        authorModel.firstName = book.author.firstName
        authorModel.lastName = book.author.lastName
        authorModel.birthDate = book.author.birthDate
        Author author = AuthorModel.toAuthor(authorModel)

        bookEntity.author = author.save()
        bookEntity.save()

        return bookEntity

    }

//    private Author findOrCreateAuthor(AuthorModel authorModel) {
//        // Look for an existing author
//        Author existingAuthor = Author.findByFirstNameAndLastName(authorModel.firstName, authorModel.lastName)
//
//        if (existingAuthor != null) {
//            return existingAuthor
//        } else {
//            // Create a new author if none exists
//            Author newAuthor = new Author()
//            newAuthor.firstName = authorModel.firstName
//            newAuthor.lastName = authorModel.lastName
//            newAuthor.birthDate = authorModel.birthDate // Correct assignment of birthDate
//            newAuthor.save(flush: true)
//
//            return newAuthor
//        }
//    }
    @Transactional
    def deleteById(Long id) {
        BookEntity book = BookEntity.findById(id)
        if (book) {
            book.delete()
            return "Successfully Deleted"
        } else {
            return "Book Not found"
        }

    }

    @Transactional
    def getAllBooks() {
        List<BookEntity> books = BookEntity.list().collect { book ->
            new Book(
                    title: book.title,
                    pages: book.pages,
                    pubdate: book.pubdate,
                    author: new AuthorModel(
                            firstName: book.author.firstName,
                            lastName: book.author.lastName,
                            birthDate: book.author.birthDate
                    )
            )
        }
        return books
    }

    @Transactional
    def getBookById(Long id) {
        BookEntity bookEntity = BookEntity.get(id)
        if (bookEntity) {
            Book book = new Book()
            book.title = bookEntity.title

            // Instantiate the AuthorModel and set its properties
            AuthorModel authorModel = new AuthorModel()
            authorModel.firstName = bookEntity.author.firstName
            authorModel.lastName = bookEntity.author.lastName
            authorModel.birthDate = bookEntity.author.birthDate

            // Set the authorModel in bookModel
            book.author = authorModel
            book.pages = bookEntity.pages
            book.pubdate = bookEntity.pubdate

            return book
        } else {
            return "Book Not Found"
        }
    }

    @Transactional
    def updateBook(Long id, Book updatedBook) {
        if (updatedBook == null) {
            return "Invalid book data provided"
        }

        // Find the existing book by ID
        BookEntity bookEntity = BookEntity.findById(id)

        if (bookEntity == null) {
            return "Book not found"
        }

        // Check for existing author
        Author author = null
        if (updatedBook.author != null) {
            author = Author.findByFirstNameAndLastName(
                    updatedBook.author.firstName,
                    updatedBook.author.lastName
            )
            if (author == null) {
                // Create new author if not found
                author = new Author(
                        firstName: updatedBook.author.firstName,
                        lastName: updatedBook.author.lastName,
                        birthDate: updatedBook.author.birthDate
                )
                author.save()
            }
        }

        // Update book details (excluding title)
        bookEntity.pages = updatedBook.pages
        bookEntity.pubdate = updatedBook.pubdate
        bookEntity.author = author

        try {
            // Save the updated book
            bookEntity.save(flush: true)  // Using flush to immediately persist changes
        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint violation
            return "Error updating book: ${e.message}"
        }

        return "Book updated successfully"

    }
}



