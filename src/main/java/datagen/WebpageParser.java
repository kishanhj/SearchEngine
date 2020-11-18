package datagen;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dataStructures.Trie;
import dataStructures.WebPage;
import dataStructures.config;

public class WebpageParser {

	private static int count = 1;
	private static ArrayList<String> visitedSites;

	public static void scrapeWebPage(String url,boolean dive, Trie trie) {
		if(null == visitedSites)
			visitedSites = new ArrayList<String>();
		visitedSites.add(url);

		scrapeWebPageRec(url, dive,trie);

		visitedSites.clear();
	}

	public static void scrapeWebPageRec(String url,boolean dive, Trie trie) {
		try {

			Document doc = Jsoup.connect(url).get();
			Elements newsHeadlines = doc.select("a[href]");
			
			System.out.println((count++)+"  "+url);
			String head = doc.title();
			String body = doc.body().text();

//			System.out.println(count +" head: "+head);
//			System.out.println(count +" body: "+body);
//			System.out.println();
//			System.out.println();
			
			trie.addWebPage(new WebPage(url), head.split(" "));
			trie.addWebPage(new WebPage(url), body.split(" "));

			for (Element headline : newsHeadlines) {

				String childUrl = headline.absUrl("href");
				if(visitedSites.contains(childUrl))
					continue;

				if(count > config.hops) return;

				if(dive && !visitedSites.contains(childUrl)) {
					visitedSites.add(childUrl);
					scrapeWebPageRec(childUrl,false,trie);
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
