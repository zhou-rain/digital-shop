package com.bat.qmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.bean.search.PmsSearchSkuInfo;
import com.bat.shop.api.service.pms.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ShopSearchServiceApplicationTests {

	@Reference
	SkuService skuService;

	@Autowired
	JestClient jestClient;


	@Test
	void contextLoads() {
	}


	/**
	 * function: 用api执行复杂查询
	 */
	@Test
	void search() throws IOException {

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		//bool
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		//filter
		TermQueryBuilder term = new TermQueryBuilder("skuAttrValueList.valueId","43");
		boolQueryBuilder.filter(term);

		//must
		MatchQueryBuilder match = new MatchQueryBuilder("skuName","华为");
		boolQueryBuilder.must(match);

		//query
		sourceBuilder.query(boolQueryBuilder);
		//from
		sourceBuilder.from(0);	//从第几个
		//size
		sourceBuilder.size(20);	//每页20条

		//highlight
		SearchSourceBuilder.highlight();

		String dslStr = sourceBuilder.toString();
		System.out.println("dslStr = " + dslStr);


		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

		Search search = new Search.Builder(dslStr)
				.addIndex("qmall2020pms")
				.addType("pmsSkuInfo")
				.build();

		SearchResult execute = jestClient.execute(search);

		List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

		for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {

			PmsSearchSkuInfo source = hit.source;
			pmsSearchSkuInfos.add(source);
		}

		System.out.println("pmsSearchSkuInfos = " + pmsSearchSkuInfos);




	}



	/**
	 * function: 往es中插入数据
	 */
	@Test
	public void put() throws IOException {

		//查询mysql数据
		List<PmsSkuInfo> pmsSkuInfos = skuService.getAll();

		//转化为es的数据结构
		List<PmsSearchSkuInfo> searchSkuInfos = new ArrayList<>();

		//使用beanUtil转化
		for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
			PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();

			BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);

			searchSkuInfos.add(pmsSearchSkuInfo);
		}

		//导入es
		for (PmsSearchSkuInfo searchSkuInfo : searchSkuInfos) {

			Index put = new Index.Builder(searchSkuInfo).index("qmall2020pms").type("pmsSkuInfo").id(searchSkuInfo.getId()).build();

			jestClient.execute(put);
		}

	}


}
