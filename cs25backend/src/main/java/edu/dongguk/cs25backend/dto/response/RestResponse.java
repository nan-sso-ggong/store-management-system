package edu.dongguk.cs25backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private ErrorCode error = null;

    public RestResponse(@Nullable T data) {
        this.success = true;
        this.data = data;
    }

    public static RestResponse<Object> errorResponse(ErrorCode error) {
        return new RestResponse<>(false, null, error);
    }


}
