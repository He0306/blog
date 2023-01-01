/*
 Navicat Premium Data Transfer

 Source Server         : MongoDB
 Source Server Type    : MongoDB
 Source Server Version : 40012
 Source Host           : localhost:27017
 Source Schema         : articledb

 Target Server Type    : MongoDB
 Target Server Version : 40012
 File Encoding         : 65001

 Date: 11/12/2022 14:27:51
*/


// ----------------------------
// Collection structure for comment
// ----------------------------
db.getCollection("comment").drop();
db.createCollection("comment");

// ----------------------------
// Documents of comment
// ----------------------------
db.getCollection("comment").insert([ {
    _id: ObjectId("63954e77ad39f70a7d57028d"),
    type: NumberInt("0"),
    "user_id": "1",
    "article_id": "1594328971267235841",
    "root_id": "-1",
    content: "第一条评论[微笑][微笑][微笑]",
    "to_comment_user_id": "-1",
    "to_comment_id": "-1",
    "create_time": ISODate("2022-12-11T03:28:55Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63955214f5f3f609dceb9676"),
    type: NumberInt("0"),
    "user_id": "1",
    "article_id": "1594328971267235841",
    "root_id": "63954e77ad39f70a7d57028d",
    content: "7777[太开心]",
    "to_comment_user_id": "1",
    "to_comment_id": "63954e77ad39f70a7d57028d",
    "create_time": ISODate("2022-12-11T03:44:20Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63955b4ef52f0a4e829f4d5e"),
    type: NumberInt("1"),
    "user_id": "1",
    "article_id": "",
    "root_id": "-1",
    content: "第一条友链[哈欠][哈欠][哈欠]",
    "to_comment_user_id": "-1",
    "to_comment_id": "-1",
    "create_time": ISODate("2022-12-11T04:23:42Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63955ba5f52f0a4e829f4d5f"),
    type: NumberInt("0"),
    "user_id": "1592893545260216321",
    "article_id": "1594328971267235841",
    "root_id": "-1",
    content: "666[微笑][微笑][微笑]",
    "to_comment_user_id": "-1",
    "to_comment_id": "-1",
    "create_time": ISODate("2022-12-11T04:25:09Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63955badf52f0a4e829f4d60"),
    type: NumberInt("0"),
    "user_id": "1592893545260216321",
    "article_id": "1594328971267235841",
    "root_id": "63954e77ad39f70a7d57028d",
    content: "888",
    "to_comment_user_id": "1",
    "to_comment_id": "63955214f5f3f609dceb9676",
    "create_time": ISODate("2022-12-11T04:25:17Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63955bbbf52f0a4e829f4d61"),
    type: NumberInt("1"),
    "user_id": "1592893545260216321",
    "article_id": "",
    "root_id": "-1",
    content: "7777[猪][猪][猪]",
    "to_comment_user_id": "-1",
    "to_comment_id": "-1",
    "create_time": ISODate("2022-12-11T04:25:31Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
db.getCollection("comment").insert([ {
    _id: ObjectId("63956ccaf52f0a4e829f4d62"),
    type: NumberInt("1"),
    "user_id": "1",
    "article_id": "",
    "root_id": "63955b4ef52f0a4e829f4d5e",
    content: "9999[吐]",
    "to_comment_user_id": "1",
    "to_comment_id": "63955b4ef52f0a4e829f4d5e",
    "create_time": ISODate("2022-12-11T05:38:18Z"),
    _class: "com.hc.blog.entity.mongo.Comment"
} ]);
