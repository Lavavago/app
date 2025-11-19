package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.City
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel(){

    // --- ESTADOS PARA LA LISTA GENERAL DE USUARIOS ---
    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    // --- CORRECCIÓN 1: Inicializa _currentUserId a vacío.
    // Esto asegura que al inicio no se carguen los datos de prueba.
    private val _currentUserId = MutableStateFlow("")
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

    // --- ESTADOS EXPUESTOS PARA LA PANTALLA DE EDICIÓN ---
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _city = MutableStateFlow(City.ARMENIA)
    val city: StateFlow<City> = _city.asStateFlow()

    // Estado interno para guardar el ID del usuario que se está editando
    private var editingUserId: String? = null
    private var editingUserRole: Role? = null


    init {
        loadUsers()
        // --- CORRECCIÓN 2: Se elimina la llamada a loadCurrentUser() ---
        // Al eliminar loadCurrentUser(), los campos de perfil (_name, _username, etc.)
        // se mantienen vacíos, esperando a un login o registro exitoso.
    }

    // --- FUNCIONES DE ACCIÓN PARA EDICIÓN ---

    fun onNameChange(newName: String) { _name.value = newName }
    fun onUsernameChange(newUsername: String) { _username.value = newUsername }
    fun onCityChange(newCity: City) { _city.value = newCity }

    /**
     * Establece qué usuario se debe editar y recarga los datos.
     * Esta función se llamaría después de un login o al crear un nuevo usuario.
     */
    fun setCurrentUser(id: String) {
        _currentUserId.value = id
        loadCurrentUser()
    }


    /**
     * Carga los datos del usuario actual (determinado por _currentUserId) en los StateFlows para la edición.
     */
    fun loadCurrentUser() {
        val userToEdit = findById(_currentUserId.value)

        userToEdit?.let {
            // Guarda el ID y Rol del usuario que se está editando para futuras actualizaciones
            editingUserId = it.id
            editingUserRole = it.role

            // 3. Asignar los datos del usuario a los flujos de estado
            _name.value = it.name
            _username.value = it.username
            _email.value = it.email
            _city.value = it.city
        } ?: run {
            // Si no se encuentra el usuario (ej. ID vacío), limpiamos los campos
            // Nota: Aquí se está mostrando "Perfil No Cargado" si el ID es nulo/vacío
            _name.value = if (_currentUserId.value.isBlank()) "" else "Perfil No Cargado"
            _username.value = ""
            _email.value = ""
            _city.value = City.ARMENIA
            editingUserId = null
            editingUserRole = null
        }
    }

    /**
     * Guarda los cambios realizados en el usuario.
     */
    fun updateUser() {
        // Solo actualiza si sabemos qué usuario estamos editando
        val idToUpdate = editingUserId
        val roleToUpdate = editingUserRole

        if (idToUpdate != null && roleToUpdate != null) {
            val updatedUser = User(
                id = idToUpdate,
                name = _name.value,
                username = _username.value,
                city = _city.value,
                email = _email.value, // Email no editable
                role = roleToUpdate,
                // Preservar la contraseña existente si no se proporciona una nueva
                password = _users.value.find { it.id == idToUpdate }?.password ?: ""
            )

            // Simular la actualización en la lista in-memory
            _users.value = _users.value.map {
                if (it.id == idToUpdate) updatedUser else it
            }

            // Opcional: Mostrar resultado de éxito (necesitarías el StateFlow `userSaveResult`)
            println("Usuario actualizado con éxito: $updatedUser")
        } else {
            // Opcional: Mostrar resultado de error
            println("Error: No se pudo determinar el usuario a actualizar.")
        }
    }

    // --- FUNCIONES DE MANEJO DE LISTA ORIGINALES ---

    fun loadUsers(){

        _users.value = listOf(
            User(
                id = "1", // Admin
                name = "Admin",
                username = "admin",
                role = Role.ADMIN,
                city = City.BOGOTA,
                email = "admin@example.com",
                password = "admin"
            ),
            User(
                id = "2", // Juan Pérez
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

    /**
     * Crea un nuevo usuario y, crucialmente, lo establece como el usuario actual.
     */
    fun create(user: User){
        // Generar un ID simple, ya que los IDs no son generados por una DB real aquí
        val nextId = (_users.value.mapNotNull { it.id.toIntOrNull() }.maxOrNull() ?: 0) + 1
        val newUserWithId = user.copy(id = nextId.toString())

        // 1. Añadir el nuevo usuario a la lista
        _users.value = _users.value + newUserWithId

        // 2. *** CAMBIO CLAVE: Establecer el nuevo usuario como el usuario actual. ***
        setCurrentUser(newUserWithId.id)

        // El setCurrentUser() llama a loadCurrentUser(), por lo que los campos de edición
        // ahora reflejarán los datos de este nuevo usuario inmediatamente.
    }

    fun findById(id: String): User?{
        return _users.value.find { it.id == id }
    }

    fun findByEmail(email: String): User?{
        return _users.value.find { it.email == email }
    }

    fun login(username: String, password: String): User?{
        val loggedInUser = _users.value.find { it.username == username && it.password == password }
        // Si el login es exitoso, establecemos la sesión
        loggedInUser?.let {
            setCurrentUser(it.id)
        }
        return loggedInUser
    }
}