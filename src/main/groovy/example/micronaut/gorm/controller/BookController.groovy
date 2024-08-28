package example.micronaut.gorm.controller

import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.model.Book
import example.micronaut.gorm.service.BookService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller("/books")
class BookController {
    @Inject
    BookService bookService

    @Post("/add")
    def savePerson(@Body Book book)
    {
          bookService.saveAuthor(book)
        return "Book Successfully saved"
    }

    @Get
    def getAllBooks() {
        return bookService.getAllBooks()
    }
    @Delete("/{id}")
    def deleteById(@PathVariable Long id)
    {
        return bookService.deleteById(id)
    }
    @Get("/{id}")
    def bookById(@PathVariable Long id)
    {
        return bookService.getBookById(id)
    }
    @Put("/bup/{id}")
    def bookUpdate(@PathVariable Long id,@Body Book book)
    {
        return bookService.updateBook(id,book)
    }
    @Get("/bookpages/{pages}")
    def getPages(@PathVariable Long pages)
    {
        return bookService.getPages(pages)
    }
}
