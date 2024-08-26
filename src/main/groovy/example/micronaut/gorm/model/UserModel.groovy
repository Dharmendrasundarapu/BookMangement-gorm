package example.micronaut.gorm.model


import example.micronaut.gorm.domain.UserDomain

class UserModel {
    String name
    String address
    String phoneNumber

    static  UserDomain toUser(UserModel userModel)
    {
        if(userModel==null)
        {
            return  null
        }
        UserDomain userDomain=new UserDomain()
          userDomain.name=userModel.name
          userDomain.address=userModel.address
         userDomain.phoneNumber=userModel.phoneNumber

        return  userDomain

    }
}
