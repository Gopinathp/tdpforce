package org.telugudesam.cadre.objects;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.telugudesam.cadre.util.L;
import org.telugudesam.cadre.util.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.parse.ParseObject;
import com.turbomanage.storm.api.Entity;
import com.turbomanage.storm.api.Id;

@Entity
public class DevelopmentCard implements Comparable<DevelopmentCard>, Parcelable {

	@Id
	private long id;
	private String picsArray;
	private String title;
	private String subTitle;
	private String notes;
	private String sectionsArray;
	private boolean isDeleted;
	private Date createdAt;
	private Date updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPicsArray() {
		return picsArray;
	}

	public ArrayList<String> getPics() {
		ArrayList<String> urls = new ArrayList<String>();
		try {
			if (!TextUtils.isEmpty(picsArray)) {
				JSONArray array = new JSONArray(picsArray);
				for (int i = 0; i < array.length(); i++) {
					urls.add(array.getString(i));
				}
			}
		} catch (JSONException e) {
			L.print(e);
		}
		return urls;
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

	public ArrayList<String> getSections() {
		ArrayList<String> sections = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(sectionsArray);
			for (int i = 0; i < array.length(); i++) {
				sections.add(array.getString(i));
			}
		} catch (JSONException e) {
			L.print(e);
		}
		return sections;
	}

	public ParseObject toParseObject() {
		ParseObject object = new ParseObject(this.getClass().getSimpleName());
		object.put("title", title);
		object.put("subTitle", subTitle);
		object.put("notes", notes);
		object.put("picsArray", picsArray);
		object.put("sectionsArray", sectionsArray);
		return object;
	}

	public static DevelopmentCard fromParseObject(ParseObject object) {
		DevelopmentCard card = new DevelopmentCard();
		Utils.printParseObject(object);
		card.id = object.getObjectId().hashCode();
		card.title = object.getString("title");
		card.subTitle = object.getString("subTitle");
		card.notes = object.getString("notes");
		card.picsArray = object.getString("picsArray");
		card.sectionsArray = object.getString("sectionsArray");
		if (object.has("isDeleted")) {
			L.d("setting object as deleted");
			card.isDeleted = object.getBoolean("isDeleted");
		}
		card.updatedAt = object.getUpdatedAt();
		card.createdAt = object.getCreatedAt();
		return card;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int compareTo(DevelopmentCard another) {
		return another.updatedAt.compareTo(this.updatedAt);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public boolean isIsDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	protected DevelopmentCard(Parcel in) {
		id = in.readLong();
		picsArray = in.readString();
		title = in.readString();
		subTitle = in.readString();
		notes = in.readString();
		sectionsArray = in.readString();
		isDeleted = in.readByte() != 0x00;
		long tmpCreatedAt = in.readLong();
		createdAt = tmpCreatedAt != -1 ? new Date(tmpCreatedAt) : null;
		long tmpUpdatedAt = in.readLong();
		updatedAt = tmpUpdatedAt != -1 ? new Date(tmpUpdatedAt) : null;
	}

	public DevelopmentCard() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(picsArray);
		dest.writeString(title);
		dest.writeString(subTitle);
		dest.writeString(notes);
		dest.writeString(sectionsArray);
		dest.writeByte((byte) (isDeleted ? 0x01 : 0x00));
		dest.writeLong(createdAt != null ? createdAt.getTime() : -1L);
		dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1L);
	}

	@SuppressWarnings("unused")
	public static final transient Parcelable.Creator<DevelopmentCard> CREATOR = new Parcelable.Creator<DevelopmentCard>() {
		@Override
		public DevelopmentCard createFromParcel(Parcel in) {
			return new DevelopmentCard(in);
		}

		@Override
		public DevelopmentCard[] newArray(int size) {
			return new DevelopmentCard[size];
		}
	};
}
