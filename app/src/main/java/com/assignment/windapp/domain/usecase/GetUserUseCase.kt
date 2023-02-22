package com.assignment.windapp.domain.usecase

import com.assignment.windapp.common.Resource
import com.assignment.windapp.data.remote.dto.toUser
import com.assignment.windapp.domain.model.User
import com.assignment.windapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val user = userRepository.getUser().toUser()
        } catch (exception: HttpException) {
            emit(Resource.Error(exception.localizedMessage ?: "An unexpected error occured!"))
        } catch (exception: IOException) {
            emit(Resource.Error("Server Unreachable. Check your internet connection."))
        }
    }
}