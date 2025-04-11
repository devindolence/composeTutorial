package org.chat

import User
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.stock1
import kotlinproject.composeapp.generated.resources.stock2
import kotlinproject.composeapp.generated.resources.stock3

fun getUser(user: UserKey): User {
    when(user.pictureKey) {
        "stock1" -> return User(user.name, picture = Res.drawable.stock1)
        "stock2" -> return User(user.name, picture = Res.drawable.stock2)
        "stock3" -> return User(user.name, picture = Res.drawable.stock3)
        else -> return User(user.name, picture = null) // change default profile picture
    }
}