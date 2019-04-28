package ir.idpz.taxi.driver.Classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTaxi {

    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("taxi_id")
    @Expose
    private Integer taxiId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("pelak")
    @Expose
    private String pelak;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("code_meli")
    @Expose
    private String codeMeli;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("taxi_code")
    @Expose
    private String taxiCode;
    @SerializedName("lines")
    @Expose
    private String lines;
    @SerializedName("Stations")
    @Expose
    private List<Station> stations = null;

    @SerializedName("api_token")
    @Expose
    private String api_token;


    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(Integer taxiId) {
        this.taxiId = taxiId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public String getPelak() {
        return pelak;
    }

    public void setPelak(String pelak) {
        this.pelak = pelak;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCodeMeli() {
        return codeMeli;
    }

    public void setCodeMeli(String codeMeli) {
        this.codeMeli = codeMeli;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTaxiCode() {
        return taxiCode;
    }

    public void setTaxiCode(String taxiCode) {
        this.taxiCode = taxiCode;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

}