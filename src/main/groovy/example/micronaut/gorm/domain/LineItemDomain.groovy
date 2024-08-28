package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

@Entity
class LineItemDomain {

    static belongsTo = [book: BookEntity, order: OrderDomain]


}
