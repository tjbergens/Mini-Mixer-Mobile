package ucf.eel4915l.mini_mixermobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatus {

    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
