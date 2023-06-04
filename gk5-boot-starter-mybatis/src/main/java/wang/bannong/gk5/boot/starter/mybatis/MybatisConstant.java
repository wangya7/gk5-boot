package wang.bannong.gk5.boot.starter.mybatis;

public interface MybatisConstant {
    String MASTER = "master";
    String SLAVE  = "slave";

    String MASTER_SQL_SESSION_TEMPLATE = "masterSqlSessionTemplate";
    String SLAVE_SQL_SESSION_TEMPLATE  = "slaveSqlSessionTemplate";

    String MAPPER_XML_PATH = "mybatis.mapperXmlPath";
}
