// package com.wf.seeker.mytest_cases.testEs;
//
// import javax.annotation.PostConstruct;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
// import org.springframework.stereotype.Component;
//
// import com.wf.seeker.entity.Item;
//
/// **
// * 初始化索引
// *
// * @author Fan.W
// * @since 1.8
// */
// @Component("initData")
// public class EInitData {
// private static final Logger logger = LoggerFactory.getLogger(EInitData.class);
// @Autowired
// private ElasticsearchTemplate elasticsearchTemplate;
//
// @PostConstruct
// public void init() {
// // 判断索引是否存在，如果存在，则删除
// if (elasticsearchTemplate.indexExists(Item.class)) {
// elasticsearchTemplate.deleteIndex(Item.class);
// }
// // 如果不存在，则创建。创建索引，会根据Item类的@Document注解信息来创建
// elasticsearchTemplate.createIndex(Item.class);
// logger.info("创建索引完成！！");
//
// }
// }
