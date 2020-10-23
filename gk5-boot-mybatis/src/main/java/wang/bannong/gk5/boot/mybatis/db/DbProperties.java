package wang.bannong.gk5.boot.mybatis.db;

public class DbProperties {
    // 用于区分DataSource
    private String key;
    private String host;
    private int    port;
    private String db;
    private String username;
    private String password;
    private int    minIdle;
    private int    maxIdle;
    private int    connectionTimeout;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public String toString() {
        return "DbProperties{" +
                "key='" + key + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", db='" + db + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", minIdle=" + minIdle +
                ", maxIdle=" + maxIdle +
                ", connectionTimeout=" + connectionTimeout +
                '}';
    }
}
