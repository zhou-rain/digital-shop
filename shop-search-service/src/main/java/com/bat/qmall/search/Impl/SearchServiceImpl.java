package com.bat.qmall.search.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.qmall.utils.Validator;
import com.bat.shop.api.bean.search.PmsSearchParam;
import com.bat.shop.api.bean.search.PmsSearchSkuInfo;
import com.bat.shop.api.mapper.pms.PmsSearchMapper;
import com.bat.shop.api.service.pms.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:19
 */
@Service
@Component
public class SearchServiceImpl implements SearchService {

	@Autowired
	JestClient jestClient;

	/**
	 * 首页搜索功能
	 * @param param
	 * @return
	 */
	@Override
	public List<PmsSearchSkuInfo> list(PmsSearchParam param) {

		String dslStr = this.getDslStr(param);
		System.out.println("dslStr = " + dslStr);

		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
		try {

			Search search = new Search.Builder(dslStr)
					.addIndex("qmall2020pms")
					.addType("pmsSkuInfo")
					.build();

			SearchResult execute = jestClient.execute(search);

			List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

			for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {

				PmsSearchSkuInfo source = hit.source;

				//如果没有搜索，则高亮显示
				if(Validator.isNotEmpty(param.getKeyword())){
					Map<String, List<String>> highlight = hit.highlight;
					String skuName = highlight.get("skuName").get(0);
					source.setSkuName(skuName);
				}

				pmsSearchSkuInfos.add(source);
			}

			System.out.println("pmsSearchSkuInfos = " + pmsSearchSkuInfos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pmsSearchSkuInfos;
	}


	/**
	 * 拼接dsl语句
	 * @return
	 */
	private String getDslStr(PmsSearchParam param){
		String[] valueIds = param.getValueId();
		String catalog3Id = param.getCatalog3Id();
		String keyword = param.getKeyword();

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		//bool
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		//filter
		if(Validator.isNotEmpty(catalog3Id)){
			TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",catalog3Id);
			boolQueryBuilder.filter(termQueryBuilder);
		}

		if(Validator.isNotEmpty(valueIds)){
			for (String valueId : valueIds) {
				TermQueryBuilder term = new TermQueryBuilder("skuAttrValueList.valueId",valueId);
				boolQueryBuilder.filter(term);
			}
		}

		//must
		if(Validator.isNotEmpty(keyword)){
			MatchQueryBuilder match = new MatchQueryBuilder("skuName",keyword);
			boolQueryBuilder.must(match);
		}

		//query
		sourceBuilder.query(boolQueryBuilder);
		//from
		sourceBuilder.from(0);	//从第几个
		//size
		sourceBuilder.size(20);	//每页20条

		//highlight
		HighlightBuilder highlightBuilder = new HighlightBuilder();

		highlightBuilder.preTags("<span style='color:red;background:yellow'>");
		highlightBuilder.field("skuName");
		highlightBuilder.postTags("</span>");

		sourceBuilder.highlighter(highlightBuilder);

		//sort 排序
		sourceBuilder.sort("id",SortOrder.DESC);

		//aggs 聚合函数		terms别名   field字段
		//TermsAggregationBuilder field = AggregationBuilders.terms("group_attr").field("skuAttrValueList.valueId");
		//sourceBuilder.aggregation(field);

		return sourceBuilder.toString();
	}





}
