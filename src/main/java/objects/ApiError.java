package objects;

import utils.DateTimeUtils;
import java.util.Date;

/**
 * Class that is for converting Json response from API call to APIError object
 */
public class ApiError {

    private Long timestamp;
    private Integer status;
    private String error;
    private String exception;
    private String message;
    private String path;

    /**
     * This getter convert timestamp from JSON response which is in milliseconds into date
     */
    public Date getTimestamp() {
        if(timestamp == null){
          return null;
        } else {
            return DateTimeUtils.getDateTime(timestamp);
        }
    }

    // Getters and Setters
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(Date date) {
        if(timestamp == null){
            timestamp = null;
        } else {
            timestamp = date.getTime();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ApiError {" +
                "Timestamp=" + getTimestamp() +
                ", Status=" + getStatus() +
                ", Error='" + getError() + '\'' +
                ", Exception='" + getException() + '\'' +
                ", Message='" +getMessage() + '\'' +
                ", Path='" + getPath() + '\'' +
                '}';
    }
}
