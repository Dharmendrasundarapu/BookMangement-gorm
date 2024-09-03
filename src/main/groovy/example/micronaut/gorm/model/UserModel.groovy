package example.micronaut.gorm.model


import example.micronaut.gorm.domain.UserDomain

class UserModel {
    Long id
    String name
    String address
    Long phoneNumber
    String email
    String password
    static  UserModel fromUserDomain(UserDomain userDomain)
    {
        if (userDomain==null)
        {
            return  null
        }
        return  new UserModel(
                id:userDomain.id,
                name: userDomain.name,
                address: userDomain.address,
                phoneNumber: userDomain.phoneNumber,
                email: userDomain.email
        )
    }

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
        userDomain.email=userModel.email
        userDomain.password=userModel.password

        return  userDomain

    }
}
