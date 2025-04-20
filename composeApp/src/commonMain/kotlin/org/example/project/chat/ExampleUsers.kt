package org.example.project.chat

import UserProfile
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.stock1
import kotlinproject.composeapp.generated.resources.stock2
import kotlinproject.composeapp.generated.resources.stock3

fun getUser(user: UserKey): UserProfile {
    when(user.pictureKey) {
        "stock1" -> return UserProfile(user.name, picture = Res.drawable.stock1)
        "stock2" -> return UserProfile(user.name, picture = Res.drawable.stock2)
        "stock3" -> return UserProfile(user.name, picture = Res.drawable.stock3)
        else -> return UserProfile(user.name, picture = null) // change default profile picture
    }
}