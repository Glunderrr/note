package files.domain.usecases

import files.domain.models.UserModel
import files.domain.repository.UserRepository

class SaveDataUseCase(private val userDataRepository: UserRepository) {
    fun execute(data: UserModel): Boolean {
        val oldData = userDataRepository.getData()

        if(data == oldData) return true
        return userDataRepository.saveData(data)
    }
}