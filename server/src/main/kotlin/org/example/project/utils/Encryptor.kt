package org.example.project.utils

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}

fun verifyPassword(plain: String, hashed: String): Boolean {
    return BCrypt.checkpw(plain, hashed)
}