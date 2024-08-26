package example.micronaut.gorm.service


import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton


@Singleton
class UserService {

    @Transactional
    def createUser(UserModel userModel)
    {
        UserDomain userDomain=new UserDomain()
        userDomain=userModel.toUser(userModel)
        userDomain.save()

        return  userDomain
    }
}
