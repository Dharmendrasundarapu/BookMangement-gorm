package example.micronaut.gorm.service

import example.micronaut.gorm.ApplicationConstants.Constants
import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.handlers.UserNotFound
import example.micronaut.gorm.model.UserModel
import grails.gorm.transactions.Transactional
import org.hibernate.SessionFactory

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserService {



    @Transactional
    def createUser(UserModel userModel)
    {
        UserDomain userDomain=userModel.toUser(userModel)
        userDomain.save()

        return  userDomain
    }
    @Transactional
    def getAllUsers()
    {
        List<UserDomain> userDomains=UserDomain.findAll()

        return  userDomains.collect{UserModel.fromUserDomain(it)}
    }
    @Transactional
    def userById(Long id)
    {
        UserDomain userDomain=UserDomain.get(id)
        if(userDomain)
        {
            return UserModel.fromUserDomain(userDomain)
        }
        else {
            throw new UserNotFound(Constants.USER_NOT_FOUND)
        }
    }
    @Transactional
    def updateUser(Long id,UserModel updateduserModel)
    {
        if(updateduserModel==null)
        {
            return "Invalid data"
        }
        UserDomain userDomain=UserDomain.get(id)
        if(UserDomain==null)
        {
            return "No user"
        }
        userDomain.name=updateduserModel.name
        userDomain.address=updateduserModel.address
        userDomain.phoneNumber=updateduserModel.phoneNumber

        userDomain.save()

        return userDomain

    }
    @Transactional
    def deleteUser(Long id)
    {
        UserDomain userDomain=UserDomain.get(id)
        if(userDomain)
        {
            userDomain.delete()
            return true
        }
        else
        {
            return "No user with ${id}"
        }
    }
    @Transactional
    def getDetails(String email,String password)
    {
        UserDomain userDomain=UserDomain.findByEmailAndPassword(email,password)
        if(userDomain)
        {
            return  UserModel.fromUserDomain(userDomain)
        }
        else
        {
            return "Invalid credentials"
        }
    }


}
