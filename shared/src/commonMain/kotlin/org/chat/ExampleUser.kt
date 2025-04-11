package org.chat

import User
import kotlinproject.shared.generated.resources.Res

val myUser = User("Me", picture = null)
val friends = listOf(
    User("Alex", picture = Res.drawable.stock1),
    User("Casey", picture = Res.drawable.stock2),
    User("Sam", picture = Res.drawable.stock3)
)
val friendMessages = listOf(
    "How's everybody doing today?",
    "I've been meaning to chat!",
    "When do we hang out next? 😋",
    "We really need to catch up!",
    "It's been too long!",
    "I can't\nbelieve\nit! 😱",
    "Did you see that ludicrous\ndisplay last night?",
    "We should meet up in person!",
    "How about a round of pinball?",
    "I'd love to:\n🍔 Eat something\n🎥 Watch a movie, maybe?\nWDYT?"
)