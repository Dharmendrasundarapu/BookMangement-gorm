package example.micronaut.gorm.service

import example.micronaut.gorm.domain.Author
import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.model.AuthorModel
import example.micronaut.gorm.model.Book
import grails.gorm.transactions.Transactional

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
        BookEntity bookEntity=new BookEntity()
        bookEntity.title=book.title
        bookEntity.pubdate=book.pubdate
        bookEntity.pages=book.pages
        AuthorModel authorModel=new AuthorModel()
        authorModel.firstName=book.author.firstName
        authorModel.lastName=book.author.lastName
        authorModel.birthDate=book.author.birthDate
        Author author=AuthorModel.toAuthor(authorModel)

        bookEntity.author= author.save()
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
    def deleteById(Long id)
    {
        BookEntity book=BookEntity.findById(id)
        if (book)
        {
            book.delete()
            return "Successfully Deleted"
        }
        else {
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

   def updateOrCreateBook(Long id, Book book) {
       // Try to retrieve the existing book entity by its ID
       BookEntity bookEntity = BookEntity.get(id)

       if (bookEntity) {
           // If the book exists, update its fields
           bookEntity.title = book.title
           bookEntity.pubdate = book.pubdate
           bookEntity.pages = book.pages

           if (book.author) {
               AuthorModel authorModel = new AuthorModel()
               authorModel.firstName = book.author.firstName
               authorModel.lastName = book.author.lastName
               authorModel.birthDate = book.author.birthDate

               Author author = AuthorModel.toAuthor(authorModel)

               bookEntity.author = author.save()
           } else {
               throw new RuntimeException("Author information is missing")
           }

           bookEntity.save()
           return "Updated Successfully"
       } else {
           // If the book doesn't exist, create a new book entity
           BookEntity newBookEntity = new BookEntity()
           newBookEntity.title = book.title
           newBookEntity.pubdate = book.pubdate
           newBookEntity.pages = book.pages

           if (book.author) {
               AuthorModel authorModel = new AuthorModel()
               authorModel.firstName = book.author.firstName
               authorModel.lastName = book.author.lastName
               authorModel.birthDate = book.author.birthDate

               Author author = AuthorModel.toAuthor(authorModel)

               newBookEntity.author = author.save()
           } else {
               throw new RuntimeException("Author information is missing")
           }

           newBookEntity.save()
           return "Created Successfully"
       }
   }



}



