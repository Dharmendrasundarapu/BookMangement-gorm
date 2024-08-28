package example.micronaut.gorm.controller

import example.micronaut.gorm.model.OrderModel
import example.micronaut.gorm.service.OrderService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject
import java.time.LocalDate


@Controller("/orders")
class OrderController {
    @Inject
    OrderService orderService
    @Post
    def saveOrder(@Body OrderModel orderModel)
    {
        return  orderService.createOrder(orderModel)
    }
    @Get("/{orderId}")
    def ordersById(@PathVariable Long orderId)
    {
        return  orderService.getOrdersById(orderId)
    }
    @Get
    def allOrders()
    {
        return orderService.getAllOrders()
    }
    @Get("/userId/{userId}")
    def userDetails(@PathVariable Long userId)
    {
        return orderService.getByUserId(userId)
    }
    @Put("/update/{id}")
    def orderUpdate(@PathVariable Long id,@Body OrderModel orderModel)
    {
        return orderService.updateOrder(id,orderModel)
    }
    @Delete("/{id}")
    def orderDeletion(@PathVariable Long id)
    {
        return  orderService.deleteOrder(id)
    }

}
