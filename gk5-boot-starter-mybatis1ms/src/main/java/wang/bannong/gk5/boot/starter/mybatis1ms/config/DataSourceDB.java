package wang.bannong.gk5.boot.starter.mybatis1ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wang.bannong(inc11003307@gmail.com)
 */
@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSourceDB {

    public static String PLACEHOLDER_NAME_MAPPERS_PATH = "datasource.mappersPath";

    private DB     master;
    private DB     slave;

    public DB getMaster() {
        return master;
    }

    public void setMaster(DB master) {
        this.master = master;
    }

    public DB getSlave() {
        return slave;
    }

    public void setSlave(DB slave) {
        this.slave = slave;
    }

    @Override
    public String toString() {
        return "DataSourceDB{" +
                "master=" + master +
                ", slave=" + slave +
                '}';
    }

    public static class DB {
        private String host;
        private int    port;
        private String db;
        private String username;
        private String password;
        private int    minIdle;
        private int    maxIdle;
        private int    maxActive;
        private long   connectionTimeout;

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

        public long getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        @Override
        public String toString() {
            return "DB{" +
                    "host='" + host + '\'' +
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
}
