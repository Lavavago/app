package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.City
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel(){

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers(){

        _users.value = listOf(
            User(
                id = "1",
                name = "Admin",
                username = "admin",
                role = Role.ADMIN,
                city = City.BOGOTA,
                email = "admin@example.com",
                password = "admin"
            ),
            User(
                id = "2",
                name = "Juan Pérez",
                username = "juanp",
                role = Role.USER,
                city = City.ARMENIA,
                email = "juanp@example.com",
                password = "pass123"
            ),
            User(
                id = "3",
                name = "María Gómez",
                username = "mariag",
                role = Role.USER,
                city = City.PEREIRA,
                email = "mariag@example.com",
                password = "pass456"
            )
        )

    }

    fun create(user: User){
        _users.value = _users.value + user
    }

    fun findById(id: String): User?{
        return _users.value.find { it.id == id }
    }

    fun findByEmail(email: String): User?{
        return _users.value.find { it.email == email }
    }

    fun login(username: String, password: String): User?{
        return _users.value.find { it.username == username && it.password == password }
    }

}