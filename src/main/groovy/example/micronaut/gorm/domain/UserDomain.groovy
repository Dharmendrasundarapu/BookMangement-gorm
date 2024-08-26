package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity


@Entity
class UserDomain {
    String name
    String address
    Long phoneNumber
    String email
    String password


    static  mapping={
        id generator:'increment'
        phoneNumber column:"phNum"
    }
    static constraints={
        name blank:false,unique:true
        address blank:false
        phoneNumber nullable:false,size:10
        email blank:false,unique:true
        password blank:false,unique:true
    }


}
