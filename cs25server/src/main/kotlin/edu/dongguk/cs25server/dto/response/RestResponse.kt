package edu.dongguk.cs25server.dto.response

import edu.dongguk.cs25server.exception.ErrorCode

class RestResponse<T> (
    val success: Boolean,
    val data: T? = null,
    val error: ErrorCode? = null
) {

    constructor(data: T?) : this(true, data, null)

    companion object{
        fun errorResponse(error: ErrorCode?): RestResponse<Any> {
            return RestResponse(false, null, error)
        }
    }

}