package com.root14.cryptocurrencytracker.network.state

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""

)