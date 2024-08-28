package example.micronaut.gorm.model

import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.domain.OrderDomain
import example.micronaut.gorm.domain.UserDomain
import grails.gorm.transactions.Transactional

import java.time.LocalDate

class OrderModel {
    Long orderId
    Long userId
    LocalDate orderDate
    List<Integer> bookIds




}
