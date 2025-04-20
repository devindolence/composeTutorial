package org.example.project.chat

data class UserKey(
    val name: String,
    val pictureKey: String?
)

val myUser = UserKey("Me", pictureKey = null)
val friends = listOf(
    UserKey("Alex", "stock1"),
    UserKey("Casey", "stock2"),
    UserKey("Sam", "stock3")
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