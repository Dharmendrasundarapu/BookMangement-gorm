package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BookEntity
import example.micronaut.gorm.domain.LineItemDomain
import example.micronaut.gorm.domain.OrderDomain
import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.OrderModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton
import java.time.LocalDate

@Singleton
class OrderService {

    @Transactional
    def createOrder(OrderModel orderModel) {

        OrderDomain orderDomain=new OrderDomain()
        orderDomain.orderDate=orderModel.orderDate

        orderDomain.user=UserDomain.get(orderModel.userId)
        orderDomain.save()

        orderModel.bookIds.each {bookId->
            LineItemDomain lineItemDomain=new LineItemDomain()
            lineItemDomain.order=orderDomain
            lineItemDomain.book=BookEntity.get(bookId)
            lineItemDomain.save()
        }
        return "Your order id  is ${orderDomain.id}"
    }
    @Transactional
    def getOrdersById(Long orderId)
    {
        OrderDomain orderDomain=OrderDomain.findById(orderId)
        if(orderDomain==null)
        {
              return "No Orders with this ${orderId}"
        }
        OrderModel orderModel=new OrderModel()
        orderModel.orderId= orderDomain.id
        orderModel.orderDate=orderDomain.orderDate
        orderModel.userId=orderDomain.userId
        orderModel.bookIds=[]

        orderDomain.lineItems.each{lineItem->
            orderModel.bookIds.add(lineItem.book.id as Integer)
        }

          return  orderModel
    }
    @Transactional
    def getAllOrders()
    {
      def orders=OrderDomain.findAll()
        if(orders==null)
        {
            return null
        }

        def orderModels=[]

        orders.each { order ->

            OrderModel orderModel = new OrderModel()
            orderModel.orderId = order.id
            orderModel.orderDate= order.orderDate
            orderModel.bookIds =[]

            order.lineItems.each { lineItem ->
                orderModel.bookIds.add(lineItem.book.id as Integer)
            }


            orderModels.add(orderModel)


        }
        return  orderModels

    }
    @Transactional
    def getByUserId(Long userId) {
        UserDomain userDomain = UserDomain.get(userId)  // Changed from findById to get

        if (userDomain == null) {
            return null
        }

        def orders = OrderDomain.findAllByUser(userDomain)
        def orderModels=[]

        orders.each { order ->

            OrderModel orderModel = new OrderModel()
            orderModel.orderId = order.id
            orderModel.orderDate= order.orderDate
        orderModel.bookIds =[]

            order.lineItems.each { lineItem ->
                orderModel.bookIds.add(lineItem.book.id as Integer)
            }


            orderModels.add(orderModel)


        }
        return  orderModels
    }


}
