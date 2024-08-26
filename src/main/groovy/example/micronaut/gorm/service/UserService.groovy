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
    @Transactional
    def getAllUsers()
    {
        List<UserDomain> userDomains=UserDomain.findAll()

        return  userDomains
    }
    @Transactional
    def userById(Long id)
    {
        UserDomain userDomain=UserDomain.get(id)
        if(userDomain)
        {
            return userDomain
        }
        else {
            return "No user with the id"
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

        return "User Updated Successfully"

    }
    @Transactional
    def deleteUser(Long id)
    {
        UserDomain userDomain=UserDomain.get(id)
        if(userDomain)
        {
            userDomain.delete()
            return "Successfully deleted"
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
            return  userDomain
        }
        else
        {
            return "Invalid credentials"
        }
    }
}
