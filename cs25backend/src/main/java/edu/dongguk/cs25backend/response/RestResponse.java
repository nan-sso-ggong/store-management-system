package edu.dongguk.cs25backend.response;

import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class RestResponse<T> {

    private final Boolean success;
    private final T data;
    @Builder.Default
    private ErrorCode error = null;

    public RestResponse(@Nullable T data) {
        this.success = true;
        this.data = data;
    }

    public static RestResponse<Object> errorResponse(ErrorCode error) {
        return new RestResponse<>(false, null, error);
    }


}
