// package com.wf.seeker.entity;
//
// import org.springframework.data.annotation.Id;
// import org.springframework.data.elasticsearch.annotations.Document;
// import org.springframework.data.elasticsearch.annotations.Field;
// import org.springframework.data.elasticsearch.annotations.FieldType;
//
// import lombok.Data;
//
// @Data
// @Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
// public class Item {
// /**
// * @Description: @Id注解必须是springframework包下的 org.springframework.data.annotation.Id
// * @Author: https://blog.csdn.net/chen_2890
// */
//
// @Id
// private Long id;
//
// /**
// * 标题
// */
// @Field(type = FieldType.Text, analyzer = "ik_max_word")
// private String title;
//
// /**
// * 分类
// */
// @Field(type = FieldType.Text)
// private String category;
//
// /**
// * 品牌
// */
// @Field(type = FieldType.Text)
// private String brand;
//
// /**
// * 价格
// */
// @Field(type = FieldType.Float)
// private Double price;
//
// /**
// * 图片地址
// */
// @Field(index = false, type = FieldType.Text)
// private String images;
// }
