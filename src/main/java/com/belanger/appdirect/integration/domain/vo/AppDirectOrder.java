package com.belanger.appdirect.integration.domain.vo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class AppDirectOrder {
	private String editionCode;
	private String addonOfferingCode;
	private String pricingDuration;
	private List<Map<String, String>> items;

	public String getEditionCode() {
		return editionCode;
	}

	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}

	public String getAddonOfferingCode() {
		return addonOfferingCode;
	}

	public void setAddonOfferingCode(String addonOfferingCode) {
		this.addonOfferingCode = addonOfferingCode;
	}

	public String getPricingDuration() {
		return pricingDuration;
	}

	public void setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
	}

	public List<Map<String, String>> getItems() {
		return items;
	}

	public void setItems(List<Map<String, String>> items) {
		this.items = items;
	}

	public Integer getItemAsInt(String key) {
		String value = getItem(key);

		if ( value == null ) 
			return null;

		return Integer.parseInt(value);
	}

	public String getItem(String key) {
		if ( items != null )
		{
			for ( Map<String, String> map : items)
			{
				Set<Entry<String, String>> entrySet = map.entrySet();
				Entry<String, String> entry = entrySet.iterator().next();
				if ( StringUtils.equalsIgnoreCase(entry.getKey(), key) )
					return entry.getValue();
			}
		}
		
		return null;
	}
}
