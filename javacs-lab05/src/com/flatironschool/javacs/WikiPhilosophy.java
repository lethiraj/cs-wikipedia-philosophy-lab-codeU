package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {


	final static WikiFetcher wf = WikiFetcher.getwikiFetcher();
	/*
	 String p=node.toString();
				int x= p.lastIndexOf('"');
				String q=p.substring(x+2,p.length());
				int y=q.lastIndexOf('<');
				String r=q.substring(0,y);
				if(r.contains("<i>")||r.contains("<em>")){
					continue;
				}
				if(isUpperCase(r.charAt(0)))
				System.out.println(p);
				System.out.println(q);
				System.out.println(r);
	 */

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
	 * 2. Ignoring external links, links to the current page, or red links
	 * 3. Stopping when reaching "Philosophy", a page with no links or a page
	 *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// some example code to get you started

		List<String> visited_url=new ArrayList<String>();
		String baseURL = "https://en.wikipedia.org";
		String url = "https://en.wikipedia.org/wiki/Anchor_text";
		visited_url.add(url);
		while(!url.equals("https://en.wikipedia.org/wiki/Philosophy")){

			Elements paragraphs = wf.fetchWikipedia(url);

			Element firstPara = paragraphs.get(0);
			int loopFlag = -1;
			Iterable<Node> iter = new WikiNodeIterable(firstPara);
			for(Node node: iter){
				if(node instanceof Element) {
					Boolean anchor = node.nodeName().equals("a");
					Boolean paraParent = node.parent().nodeName().equals("p");
					Boolean lowerCase = Character.isLowerCase(((Element) node).text().charAt(0));

					if(anchor && paraParent && lowerCase){
						String linkURL = node.attr("href");
						url = baseURL + linkURL;
						if(visited_url.contains(url)){
							loopFlag = 1;
							break;
						}

						visited_url.add(url);
						break;

					}
				}
			}
			if(loopFlag == 1){
				break;
			}
			System.out.println(url);
		}
		System.out.println(visited_url);
	}
}





