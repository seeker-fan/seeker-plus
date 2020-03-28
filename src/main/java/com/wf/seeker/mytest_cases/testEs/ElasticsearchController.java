// package com.wf.seeker.mytest_cases.testEs;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
//
// import javax.annotation.PostConstruct;
// import javax.servlet.http.HttpServletRequest;
//
// import org.apache.commons.lang3.StringUtils;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.common.text.Text;
// import org.elasticsearch.index.query.BoolQueryBuilder;
// import org.elasticsearch.index.query.DisMaxQueryBuilder;
// import org.elasticsearch.index.query.MatchQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.elasticsearch.search.SearchHit;
// import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
// import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
// import org.elasticsearch.search.sort.SortBuilders;
// import org.elasticsearch.search.sort.SortOrder;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.DependsOn;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
// import org.springframework.data.elasticsearch.core.SearchResultMapper;
// import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
// import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
// import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
// import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
// import org.springframework.data.elasticsearch.core.query.SearchQuery;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.ModelAndView;
//
// import com.wf.seeker.entity.Item;
// import com.wf.seeker.mapper.ItemRepository;
// import com.wf.seeker.mapper.TestMapper;
// import com.wf.seeker.util.JsonUtil;
//
/// **
// * 根据官方文档测试常用的api 文档地址:https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
// */
// @RestController
// @DependsOn(value = "itemRepository")
// public class ElasticsearchController {
//
// private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);
// @Autowired
// private ElasticsearchTemplate elasticsearchTemplate;
// @Autowired
// private ItemRepository itemRepository;
//
//	//@formatter:off
//	@RequestMapping("/index")
//	public ModelAndView index(ModelAndView model, HttpServletRequest request) throws Exception {
//		String title = request.getParameter("title");
//		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//		// 给title字段更高的权重
//		// queryBuilder.should(QueryBuilders.matchQuery("title", title).boost(3));
//		if(StringUtils.isEmpty(title)) {
//			queryBuilder.should(QueryBuilders.matchAllQuery());
//		}else {
//			queryBuilder.should(QueryBuilders.matchQuery("title", title));
//		}
//		// description 默认权重 1
//		// queryBuilder.should(QueryBuilders.matchQuery("category", category));
//		// 至少一个should条件满足
//		// queryBuilder.minimumShouldMatch(1);
//		HighlightBuilder.Field titleField = new HighlightBuilder.Field("title").preTags("<span style='color:red;background:yellow' >").postTags("</span>");
//
//		
//		
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(queryBuilder)
//				//.withPageable(PageRequest.of(0, 20))
//				.withHighlightFields(titleField)
//				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
//				.build();
//
//		//logger.info("\n search(): searchContent [" + title + "] \n DSL  = \n " + searchQuery.getQuery().toString());
//		
//		//高亮显示关键字   https://blog.csdn.net/u014799292/article/details/88547580
//		AggregatedPage<Item> queryForPage = elasticsearchTemplate.queryForPage(searchQuery, Item.class,
//				new SearchResultMapper() {
//	 
//					@Override
//					public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
//							Pageable pageable) {
//	 
//						List<Item> list = new ArrayList<Item>();
//						for (SearchHit searchHit : response.getHits()) {
//							if (response.getHits().getHits().length <= 0) {
//								return null;
//							}
//							Item answer = JsonUtil.jsonToObj(searchHit.getSourceAsString(), Item.class);
//							Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
//							//匹配到的title字段里面的信息
//							HighlightField titleHighlight = highlightFields.get("title");
//							if (titleHighlight != null) {
//								Text[] fragments = titleHighlight.fragments();
//								String fragmentString = fragments[0].string();
//								answer.setTitle(fragmentString);
//							}
//							list.add(answer);
//	 
//						}
//						if (list.size() > 0) {
//							return new AggregatedPageImpl<T>((List<T>) list);
//						}
//						return null;
//					}
//
//					@Override
//					public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
//						// TODO Auto-generated method stub
//						return null;
//					}
//				});
//		List<Item> list = queryForPage.getContent();
//		
//		//Page<Item> page = itemRepository.search(searchQuery);
//		// 总条数
////		logger.info(page.getTotalElements() + "");
////		
////
////		Iterator<Item> iterator = page.iterator();
////		while (iterator.hasNext()) {
////			logger.info(iterator.next().toString());
////		}
//
//		// 总页数
//		//logger.info(page.getTotalPages() + "");
//		model.addObject("data", list);
//		model.addObject("title", title);
//		model.setViewName("index");
//		return model;
//	}
//	//@formatter:on
//
// /**
// * 返回由给定ID标识的实体
// */
// @GetMapping("/testFindById")
// @ResponseBody
// public Object testFindById(@RequestParam(name = "id") String id) {
// Optional<Item> item = itemRepository.findById(Long.valueOf(id));
// logger.info(item.get().toString());
// return item;
// }
//
// /**
// * 测试查询高亮 参考文章：https://www.cnblogs.com/vcmq/p/9966693.html
// */
// @GetMapping("/testHighlightQuery")
// @ResponseBody
// public Object testHighlightQuery(@RequestParam(name = "keyword") String keyword) {
//
// BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
// // 至少一个should条件满足
// boolQuery.minimumShouldMatch(1);
//
// NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(boolQuery)
// .withHighlightFields(
// new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"),
// new HighlightBuilder.Field("brand").preTags("<span style=\"color:red\">").postTags("</span>"))
// .withPageable(PageRequest.of(0, 10));
// // 最佳字段 + 降低除了name之外字段的权重系数
// MatchQueryBuilder nameQuery = QueryBuilders.matchQuery("title", keyword).analyzer("ik_max_word");
// MatchQueryBuilder authorQuery = QueryBuilders.matchQuery("brand", keyword).boost(0.8f);
// DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery().add(nameQuery).add(authorQuery);
// queryBuilder.withQuery(disMaxQueryBuilder);
//
// NativeSearchQuery searchQuery = queryBuilder.build();
// Page<Item> items = elasticsearchTemplate.queryForPage(searchQuery, Item.class);
//
// items.forEach(e -> logger.info("{}", e));
// return items;
// }
//
// @Autowired
// private TestMapper testMapper;
//
// // 数据初始化不能和索引初始化写在一起，否则会报mapping conflict
// @PostConstruct
// public void init() {
// List<Item> items = testMapper.selectItems();
// items.forEach(x -> itemRepository.index(x));
// logger.info("elasticsearch 初始化数据完成！！！");
// }
// }
