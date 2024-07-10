package files.app.data.repository

import files.app.data.storage.User
import files.app.data.storage.UserStorage
import files.domain.models.UserModel
import files.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {

    override fun saveData(data: UserModel): Boolean {
        return userStorage.save(mapToStorage(data))
    }

    override fun getData(): UserModel {
        return mapToDomain(userStorage.get())
    }

    private fun mapToDomain(user: User): UserModel {
        return UserModel(user.firstName, user.lastName)
    }

    private fun mapToStorage(userDataModel: UserModel): User {
        return User(userDataModel.name, userDataModel.lastName)
    }
}