package org.telugudesam.cadre.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.telugudesam.cadre.R;
import org.telugudesam.cadre.components.MemCache;
import org.telugudesam.cadre.objects.DevelopmentCard;
import org.telugudesam.cadre.objects.Section;
import org.telugudesam.cadre.util.L;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DevelopmentCardsAdapter extends BaseAdapter {

	private ArrayList<DevelopmentCard> cards;
	private WeakReference<Activity> activityRef;
	private LayoutInflater inflater;

	public DevelopmentCardsAdapter(Activity activity, Section section) {
		L.d("DevelopmentCardsAdapter: section = " + section.name());
		this.activityRef = new WeakReference<Activity>(activity);
		inflater = LayoutInflater.from(activity);
		cards = MemCache.getDevelopmentCards(section);
	}

	@Override
	public int getCount() {
		L.d("getCount = " + cards.size());
		return cards.size();
	}

	@Override
	public DevelopmentCard getItem(int position) {
		return cards.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if(v == null) {
			v = inflater.inflate(R.layout.adapter_dev_card, null);
			holder = new ViewHolder(v, getItem(position));
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
			holder.updateView(getItem(position));
		}
		return v;
	}
	
	public class ViewHolder {

		private ImageView picImageView;
		private TextView titleTextView;

		public ViewHolder(View v, DevelopmentCard item) {
			picImageView = (ImageView) v.findViewById(R.id.picImageView);
			titleTextView = (TextView) v.findViewById(R.id.thank_you_textview);
			updateView(item);
		}

		public void updateView(DevelopmentCard item) {
//			Picasso.with(activityRef.get()).load(item.getPicsArray()).into(picImageView);
			titleTextView.setText(item.getTitle());
		}

	}
}
