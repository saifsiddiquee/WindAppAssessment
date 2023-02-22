package com.assignment.windapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserInfo(

    @SerializedName("Email")
    val email: String,

    @SerializedName("Id")
    val id: Int,

    @SerializedName("ProfileImage")
    val profileImage: String,

    @SerializedName("UserName")
    val userName: String,

    @SerializedName("WalletAddress")
    val walletAddress: String,

    val smartContactWallet: String
)
