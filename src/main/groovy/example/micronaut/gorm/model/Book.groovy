package example.micronaut.gorm.model

import example.micronaut.gorm.domain.Author

import java.time.LocalDate

class Book {
    String title
    AuthorModel author
    LocalDate pubdate
    Long pages
}
