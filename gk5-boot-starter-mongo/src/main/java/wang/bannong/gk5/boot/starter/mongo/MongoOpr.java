package wang.bannong.gk5.boot.starter.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

public interface MongoOpr {
    // ====================================查询方法====================================

    <T> T findOne(Query query, Class<T> entityClass, String collectionName);

    <T> List<T> find(final Query query, Class<T> entityClass, String collectionName);

    <T> T findById(Object id, Class<T> entityClass, String collectionName);

    long count(final Query query, String collectionName);

    // ====================================插入方法====================================

    void insert(Object objectToSave, String collectionName);

    void insert(Collection<? extends Object> batchToSave, String collectionName);

    // ====================================更新方法====================================

    UpdateResult updateMulti(final Query query, final Update update, String collectionName);

    // ====================================删除方法====================================

    DeleteResult remove(Query query, Class<?> entityClass, String collectionName);

    // ====================================索引方法====================================

    void createIndex(String collectionName, Document document);

    void createIndex(String collectionName, Document document, String indexName);

    void createIndex(String collectionName, Document document, String indexName, boolean unique);

    boolean collectionExists(String collectionName);

    // ====================================更新插入====================================

    UpdateResult upsert(Query query, Update update, String collectionName);

    UpdateResult upsert(Query query, Update update, Class<?> entityClass, String collectionName);

    // ====================================聚合查询====================================

    <T> AggregationResults<T> findByAggr(Aggregation aggregation, Class<T> entityClass, String collectionName);

    // ====================================MP  方法====================================

    <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass);

    <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions,
                                      Class<T> entityClass);

    <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction,
                                      Class<T> entityClass);

    <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction,
                                      MapReduceOptions mapReduceOptions, Class<T> entityClass);

    // ====================================group方法====================================

    <T> GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass);

    // ====================================Aggregation====================================
    // official documents: http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.aggregation
    <T> AggregationResults<T> aggregate(Aggregation aggregation, Class<?> inputType, Class<T> outputType);

    <T> AggregationResults<T> aggregate(Aggregation aggregation, String collectionName, Class<T> outputType);

    <T> AggregationResults<T> aggregate(TypedAggregation<?> aggregation, Class<T> outputType);

    <T> AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String inputCollectionName, Class<T> outputType);
}
