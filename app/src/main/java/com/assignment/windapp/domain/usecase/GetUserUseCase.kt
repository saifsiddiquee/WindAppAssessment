package com.assignment.windapp.domain.usecase

import com.assignment.windapp.common.Resource
import com.assignment.windapp.data.remote.dto.AuthModel
import com.assignment.windapp.data.remote.dto.ErrorDto
import com.assignment.windapp.data.remote.dto.UserDto
import com.assignment.windapp.data.remote.dto.toUser
import com.assignment.windapp.domain.model.User
import com.assignment.windapp.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(authModel: AuthModel): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val user = userRepository.getUser(authModel = authModel).toUser()
            emit(Resource.Success(user))
        } catch (exception: HttpException) {
            val err =
                Gson().fromJson(
                    exception.response()?.errorBody()?.charStream(),
                    ErrorDto::class.java
                )
            emit(Resource.Error(err.messages[0]))
        } catch (exception: IOException) {
            emit(Resource.Error("Server Unreachable. Check your internet connection."))
        }
    }
}