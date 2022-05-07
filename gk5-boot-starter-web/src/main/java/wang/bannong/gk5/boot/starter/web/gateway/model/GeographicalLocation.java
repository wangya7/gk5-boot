package wang.bannong.gk5.boot.starter.web.gateway.model;

import java.io.Serializable;

import wang.bannong.gk5.boot.starter.web.gateway.exception.GatewayException;

/**
 * geographical location
 */
public class GeographicalLocation implements Serializable {
    private static final long serialVersionUID = -7524935671757191820L;
    private final String longitude;
    private final String latitude;

    private GeographicalLocation(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static GeographicalLocation of(String gl) {
        String[] arr = gl.split(":");
        if (arr == null || arr.length != 2) {
            throw new GatewayException(
                    "illegal \'" + HttpNativeRequest.GL + "\' geographical location");
        }
        return new GeographicalLocation(arr[0], arr[1]);
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "GeographicalLocation{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}