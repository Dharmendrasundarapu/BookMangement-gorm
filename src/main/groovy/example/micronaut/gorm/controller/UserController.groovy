package example.micronaut.gorm.controller


import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import org.springframework.beans.factory.annotation.Qualifier

import javax.inject.Inject


@Controller("/user")
class UserController {

    @Inject
    UserService userService

    @Post
    def saveUser(@Body UserModel userModel)
    {
        userService.createUser(userModel)

        return "Successfully saved"
    }
    @Get
    def allBooks()
    {
        return  userService.getAllUsers()
    }
    @Get("/{id}")
    def specUser(@PathVariable Long id)
    {
        return  userService.userById(id)
    }
    @Put("/{id}")
    def userupdate(@PathVariable Long id,@Body UserModel userModel)
    {
        return  userService.updateUser(id,userModel)
    }
    @Delete("/{id}")
    def userdelete(@PathVariable Long id)
    {
        return userService.deleteUser(id)
    }
    @Get("/login")
    def userDetails(@QueryValue String email, @QueryValue String password)
    {
        return  userService.getDetails(email,password)
    }
}
