package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import org.hibernate.FetchMode

import java.time.LocalDate


@Entity
class OrderDomain {
      LocalDate orderDate

    static belongsTo = [user: UserDomain]
    static hasMany = [lineItems: LineItemDomain]

    static mapping = {
        id generator: 'increment'
        user fetch: 'join'
        lineItems fetch: 'join'

    }


}
