package edu.dongguk.cs25server.exception

class GlobalException (private val errorCode: ErrorCode) : RuntimeException() {

    fun getErrorCode() : ErrorCode = errorCode

    fun getErrorMessage() : String = errorCode.getMessage()


}