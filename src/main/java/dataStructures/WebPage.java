package dataStructures;

import java.net.MalformedURLException;
import java.net.URL;

public class WebPage {
	private URL linkUrl;
	
	public WebPage(String url) {
		try {
			linkUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public URL getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(URL linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Override
	public String toString() {
		return  linkUrl.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof WebPage))
			return false;
		
		WebPage webObj = (WebPage) obj;
		
		if(null == linkUrl && null == webObj.getLinkUrl())
			return true;
		
		return this.linkUrl.equals(webObj.getLinkUrl());
	}
}
