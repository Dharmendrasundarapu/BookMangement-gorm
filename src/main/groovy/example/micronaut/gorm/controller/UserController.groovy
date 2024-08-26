package example.micronaut.gorm.controller


import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

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
}
