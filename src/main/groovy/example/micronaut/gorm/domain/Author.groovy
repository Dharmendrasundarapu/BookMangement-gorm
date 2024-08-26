package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
@ToString(includeNames=true)
class Author {
    String firstName
    String lastName
    Date birthDate

    static hasMany = [books: BookEntity]

    static constraints = {
        firstName blank: false,unique:true
        lastName blank: false
        birthDate nullable: true
    }
}
