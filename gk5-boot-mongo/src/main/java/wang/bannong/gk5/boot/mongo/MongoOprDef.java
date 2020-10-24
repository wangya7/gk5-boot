package wang.bannong.gk5.boot.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by bn. on 2019/10/11 5:13 PM
 */
@Component("mongoOpr")
public class MongoOprDef implements MongoOpr {

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    // ====================================查询方法====================================

    @Override
    public <T> T findOne(Query query, Class<T> entityClass, String collectionName) {
        return mongoTemplate.findOne(query, entityClass, collectionName);
    }

    @Override
    public <T> List<T> find(final Query query, Class<T> entityClass, String collectionName) {
        return mongoTemplate.find(query, entityClass, collectionName);
    }

    @Override
    public <T> T findById(Object id, Class<T> entityClass, String collectionName) {
        return mongoTemplate.findById(id, entityClass, collectionName);
    }

    @Override
    public long count(final Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }

    // ====================================插入方法====================================

    @Override
    public void insert(Object objectToSave, String collectionName) {
        mongoTemplate.insert(objectToSave, collectionName);
    }

    @Override
    public void insert(Collection<? extends Object> batchToSave, String collectionName) {
        mongoTemplate.insert(batchToSave, collectionName);
    }

    // ====================================更新方法====================================

    @Override
    public UpdateResult updateMulti(final Query query, final Update update, String collectionName) {
        return mongoTemplate.updateMulti(query, update, collectionName);
    }

    // ====================================删除方法====================================

    @Override
    public DeleteResult remove(Query query, Class<?> entityClass, String collectionName) {
        return mongoTemplate.remove(query, entityClass, collectionName);
    }

    // ====================================索引方法====================================

    @Override
    public void createIndex(String collectionName, Document document) {
        createIndex(collectionName, document, null);
    }

    @Override
    public void createIndex(String collectionName, Document document, String indexName) {
        createIndex(collectionName, document, indexName, false);
    }

    @Override
    public void createIndex(String collectionName, Document document, String indexName, boolean unique) {
        Index index = new CompoundIndexDefinition(document);
        index.named(indexName);
        if (unique) {
            index.unique();
        }
        mongoTemplate.indexOps(collectionName).ensureIndex(index);
    }

    @Override
    public boolean collectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    // ====================================更新插入====================================

    @Override
    public UpdateResult upsert(Query query, Update update, String collectionName) {
        return mongoTemplate.upsert(query, update, collectionName);
    }

    @Override
    public UpdateResult upsert(Query query, Update update, Class<?> entityClass, String collectionName) {
        return mongoTemplate.upsert(query, update, entityClass, collectionName);
    }

    // ====================================聚合查询====================================

    @Override
    public <T> AggregationResults<T> findByAggr(Aggregation aggregation, Class<T> entityClass, String collectionName) {
        return mongoTemplate.aggregate(aggregation, collectionName, entityClass);
    }

    // ====================================MP  方法====================================

    @Override
    public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass) {
        return mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions,
                                             Class<T> entityClass) {
        return mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction,
                                             Class<T> entityClass) {
        return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, entityClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction,
                                             MapReduceOptions mapReduceOptions, Class<T> entityClass) {
        return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass);
    }

    // ====================================group方法====================================

    @Override
    public <T> GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass) {
        return mongoTemplate.group(criteria, inputCollectionName, groupBy, entityClass);
    }

    // ====================================Aggregation====================================
    // official documents: http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.aggregation

    @Override
    public <T> AggregationResults<T> aggregate(Aggregation aggregation, Class<?> inputType, Class<T> outputType) {
        return mongoTemplate.aggregate(aggregation, inputType, outputType);
    }

    @Override
    public <T> AggregationResults<T> aggregate(Aggregation aggregation, String collectionName, Class<T> outputType) {
        return mongoTemplate.aggregate(aggregation, collectionName, outputType);
    }

    @Override
    public <T> AggregationResults<T> aggregate(TypedAggregation<?> aggregation, Class<T> outputType) {
        return mongoTemplate.aggregate(aggregation, outputType);
    }

    @Override
    public <T> AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String inputCollectionName, Class<T> outputType) {
        return mongoTemplate.aggregate(aggregation, inputCollectionName, outputType);
    }

}
