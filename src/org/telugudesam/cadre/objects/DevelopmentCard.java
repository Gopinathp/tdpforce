package org.telugudesam.cadre.objects;

import com.turbomanage.storm.api.Entity;
import com.turbomanage.storm.api.Id;

@Entity
public class DevelopmentCard {

	@Id
	private long id;
	private String picsArray;
	private String title;
	private String subTitle;
	private String notes;
	private String sectionsArray;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPicsArray() {
		return picsArray;
	}
	public void setPicsArray(String picsArray) {
		this.picsArray = picsArray;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getSectionsArray() {
		return sectionsArray;
	}
	public void setSectionsArray(String sectionsArray) {
		this.sectionsArray = sectionsArray;
	}

	
}
