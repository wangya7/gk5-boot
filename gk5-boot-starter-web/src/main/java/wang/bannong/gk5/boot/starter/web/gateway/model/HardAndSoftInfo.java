package wang.bannong.gk5.boot.starter.web.gateway.model;

import java.io.Serializable;

import wang.bannong.gk5.boot.starter.web.gateway.exception.GatewayException;

/**
 * hardware and software information
 */
public class HardAndSoftInfo implements Serializable {
    private static final long serialVersionUID = 1512997979222379878L;

    // 厂牌机型@平台@设备系统信息@大版本号.小版本号.bugfix版本
    // HUAWEI P20@Android@Android 7.0@1.0.0
    // Apple SE@iOS@iOS11.0.0@1.0.0
    private String brand;
    private String platform;
    private String system;
    private String version;

    private HardAndSoftInfo(String brand, String platform, String system, String version) {
        this.brand = brand;
        this.platform = platform;
        this.system = system;
        this.version = version;
    }

    public static HardAndSoftInfo of(String hsi) {
        String[] arr = hsi.split("@");
        if (arr.length != 4) {
            throw new GatewayException(
                    "illegal \'" + HttpNativeRequest.HSI + "\' hardware and software information");
        }
        return new HardAndSoftInfo(arr[0], arr[1], arr[2], arr[3]);
    }

    public String getBrand() {
        return brand;
    }

    public String getPlatform() {
        return platform;
    }

    public String getSystem() {
        return system;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "HardAndSoftInfo{" +
                "brand='" + brand + '\'' +
                ", platform='" + platform + '\'' +
                ", system='" + system + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
