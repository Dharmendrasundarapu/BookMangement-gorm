package example.micronaut.gorm.controller

import example.micronaut.gorm.model.OrderModel
import example.micronaut.gorm.service.OrderService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

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


}
