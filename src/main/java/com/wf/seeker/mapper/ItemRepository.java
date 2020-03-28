// package com.wf.seeker.mapper;
//
// import java.util.List;
//
// import org.springframework.context.annotation.DependsOn;
// import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
// import org.springframework.stereotype.Component;
//
// import com.wf.seeker.entity.Item;
//
/// **
// * 定义特定于域类的存储库接口。接口必须扩展Repository并键入域类和ID类型
// */
// @Component("itemRepository")
// @DependsOn(value = "initData")
// public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
// /**
// * 根据 title 来检索
// *
// * @param title
// * @return
// */
// List<Item> findByTitleLike(String title);
//
// }
