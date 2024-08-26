package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

import java.time.LocalDate


@Entity
@ToString(includeNames=true)
class BookEntity {
    String title
    LocalDate pubdate
    String pages

     static belongsTo=[author:Author]


    static constraints={
        title blank:false,nullable:false
        pubdate nullable: true

    }
}
