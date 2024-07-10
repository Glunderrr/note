package files.domain.repository

import files.domain.models.UserModel

interface UserRepository {

    fun saveData(data: UserModel): Boolean

    fun getData(): UserModel

}