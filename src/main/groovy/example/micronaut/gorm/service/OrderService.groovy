package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.domain.LineItemDomain
import example.micronaut.gorm.domain.OrderDomain
import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.handlers.UserNotFound
import example.micronaut.gorm.model.OrderModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton
import java.time.LocalDate

@Singleton
class OrderService {

    @Transactional
    def createOrder(OrderModel orderModel) {

        OrderDomain orderDomain = new OrderDomain()
        orderDomain.orderDate = orderModel.orderDate

        orderDomain.user = UserDomain.get(orderModel.userId)
        orderDomain.save()

        orderModel.bookIds.each { bookId ->
            LineItemDomain lineItemDomain = new LineItemDomain()
            lineItemDomain.order = orderDomain
            lineItemDomain.book = BookEntity.get(bookId)
            lineItemDomain.save()
        }
        return "Your order id  is ${orderDomain.id}"
    }

    @Transactional
    def getOrdersById(Long orderId) {
        OrderDomain orderDomain = OrderDomain.findById(orderId)
        if (orderDomain == null) {
            return "No Orders with this ${orderId}"
        }
        OrderModel orderModel = new OrderModel()
        orderModel.orderId = orderDomain.id
        orderModel.orderDate = orderDomain.orderDate
        orderModel.userId = orderDomain.userId
        orderModel.bookIds = []

        orderDomain.lineItems.each { lineItem ->
            orderModel.bookIds.add(lineItem.book.id as Integer)
        }

        return orderModel
    }

    @Transactional
    def getAllOrders() {
        def orders = OrderDomain.findAll()
        if (orders == null) {
            return null
        }

        def orderModels = []

        orders.each { order ->

            OrderModel orderModel = new OrderModel()
            orderModel.orderId = order.id
            orderModel.orderDate = order.orderDate
            orderModel.bookIds = []

            order.lineItems.each { lineItem ->
                orderModel.bookIds.add(lineItem.book.id as Integer)
            }


            orderModels.add(orderModel)


        }
        return orderModels

    }

    @Transactional
    def getByUserId(Long userId) {
        UserDomain userDomain = UserDomain.get(userId)  // Changed from findById to get

        if (userDomain == null) {
            return null
        }

        def orders = OrderDomain.findAllByUser(userDomain)
        def orderModels = []

        orders.each { order ->

            OrderModel orderModel = new OrderModel()
            orderModel.orderId = order.id
            orderModel.orderDate = order.orderDate
            orderModel.bookIds = []

            order.lineItems.each { lineItem ->
                orderModel.bookIds.add(lineItem.book.id as Integer)
            }


            orderModels.add(orderModel)


        }
        return orderModels
    }

    @Transactional
    def deleteOrder(Long id) {
        OrderDomain orderDomain = OrderDomain.findById(id)
        if (orderDomain) {
            orderDomain.delete()
            return "Order Deleted Successfully"
        } else {
            return "No Order with this ${id}"
        }
    }

    @Transactional
    def updateOrder(Long orderId, OrderModel updatedOrderModel) {
        def order = OrderDomain.findById(orderId)
        if (order) {
            // Explicitly delete existing line items
            LineItemDomain.findAllByOrder(order).each { it.delete(flush: true) }

            // Add new line items based on the updated book IDs
            updatedOrderModel.bookIds.each { bookId ->
                BookEntity book = BookEntity.findById(bookId)
                if (book) {
                    LineItemDomain lineItem = new LineItemDomain(order: order, book: book)
                    order.addToLineItems(lineItem)
                } else {
                    throw new IllegalArgumentException("Book with ID ${bookId} not found")
                }
            }
            order=order.save(flush: true)
            return toOrderModel(order)
        } else {
            throw new UserNotFound("Order Not Found")
        }
    }

    static  OrderModel toOrderModel(OrderDomain orderDomain)
    {
        if(orderDomain==null)
        {
            return null
        }
        OrderModel orderModel=new  OrderModel()
        orderModel.userId=orderDomain.userId
        orderModel.orderId=orderDomain.id
        orderModel.orderDate=orderDomain.orderDate
        orderModel.bookIds=[]
        orderDomain.lineItems.each {lineItem->
            orderModel.bookIds<<(lineItem.book.id as Integer)}

        return orderModel
    }
}