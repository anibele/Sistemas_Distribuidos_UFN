public class RegistroTelefonia {

    private String session_id;
    private String imsi;
    private String imei;
    private String mcc;
    private String mnc;
    private String cell_id;
    private String lac;
    private String bearer;
    private String start_time;
    private String end_time;
    private String duration_s;
    private String ip_addr;
    private String sip_call_id;
    private String codec;
    private String bytes_up;
    private String bytes_down;
    private String lat;
    private String lon;
    private String encryption;

    public String getSession_id() {
        return session_id;
    }

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public String getCell_id() {
        return cell_id;
    }

    public String getLac() {
        return lac;
    }

    public String getBearer() {
        return bearer;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getDuration_s() {
        return duration_s;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public String getSip_call_id() {
        return sip_call_id;
    }

    public String getCodec() {
        return codec;
    }

    public String getBytes_up() {
        return bytes_up;
    }

    public String getBytes_down() {
        return bytes_down;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getEncryption() {
        return encryption;
    }
}
