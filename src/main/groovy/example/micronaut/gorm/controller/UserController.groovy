package example.micronaut.gorm.controller


import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import org.springframework.beans.factory.annotation.Qualifier

import javax.inject.Inject


@Controller("/user")
class UserController {

    @Inject
    UserService userService

    @Post
    def saveUser(@Body UserModel userModel)
    {
        try {
            def user=   userService.createUser(userModel)
            if(user)
            {
                return HttpResponse.created(user)
            }
            else {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch (Exception e)
        {
            return HttpResponse.serverError("An error occured ${e.message}")
        }



    }
    @Get
    def allBooks()
    {
        try{
            def users=  userService.getAllUsers()
            if(users)
            {
                return HttpResponse.ok(users)
            }
            else {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch (Exception e)
        {
            return HttpResponse.serverError("An error occured ${e.message}")
        }

    }
    @Get("/{id}")
    def specUser(@PathVariable Long id)
    {
        try {
            def user = userService.userById(id)
            if (user) {
                return HttpResponse.ok(user)
            }
            else
            {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch (Exception e)
        {
            return HttpResponse.serverError("An error occured ${e.message}")
        }
    }
    @Put("/{id}")
    def userupdate(@PathVariable Long id,@Body UserModel userModel)
    {
        try {
            def user=  userService.updateUser(id,userModel)
            if(user)
            {
                return HttpResponse.created(user)
            }
            else {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch (Exception e)
        {
            return HttpResponse.serverError("An error occured ${e.message}")
        }

    }
    @Delete("/{id}")
    def userdelete(@PathVariable Long id)
    {
        try {
            def user= userService.deleteUser(id)
            if(user){
                 HttpResponse.noContent()
            }
            else
          {
            return HttpResponse.badRequest("Failed to add user")
          }
        }
    catch (Exception e)
    {
        return HttpResponse.serverError("An error occured ${e.message}")
    }
    }
    @Post("/login")
    def userDetails(@Body UserModel userModel)
    {
        try {
            def use = userService.getDetails(userModel.email, userModel.password)
            if (use) {
                return HttpResponse.ok(use)
            } else {
                return HttpResponse.badRequest("Failed to send")
            }
        }
        catch (Exception e)
        {
            return HttpResponse.serverError("An error occured ${e.message}")
        }
    }
}
