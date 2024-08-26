package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity


@Entity
class UserDomain {
    String name
    String address
    String phoneNumber


    static  mapping={
        id generator:'increment'
        phoneNumber column:"phNum"
    }
    static constraints={
        name nullable:false
        address nullable:false
        phoneNumber nullable:false
    }


}
