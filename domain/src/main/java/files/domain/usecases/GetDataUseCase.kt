package files.domain.usecases

import files.domain.models.UserModel
import files.domain.repository.UserRepository

class GetDataUseCase(private val userDataRepository: UserRepository) {
    fun execute(): UserModel {
        return userDataRepository.getData()
    }
}