package common.actions;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private String response;
    private ResponseCode isGoodResponse;

    public UserResponse(ResponseCode isGoodResponse, String response) {
        this.isGoodResponse = isGoodResponse;
        this.response = response;
    }

    /**
     * @return Response body.
     */
    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "UserResponse[" + response+ "]";
    }

    public ResponseCode getIsGoodResponse() {
        return isGoodResponse;
    }
}
