package org.telugudesam.cadre.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.telugudesam.cadre.Constants.BundleExtras;
import org.telugudesam.cadre.R;
import org.telugudesam.cadre.activity.PreviewActivity;
import org.telugudesam.cadre.components.MemCache;
import org.telugudesam.cadre.objects.DevelopmentCard;
import org.telugudesam.cadre.objects.Section;
import org.telugudesam.cadre.util.L;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DevelopmentCardsAdapter extends BaseAdapter {

	private ArrayList<DevelopmentCard> cards;
	private WeakReference<Activity> activityRef;
	private LayoutInflater inflater;
	private Section section;

	public DevelopmentCardsAdapter(Activity activity, Section section) {
		this.section = section;
		L.d("DevelopmentCardsAdapter: section = " + section.name());
		this.activityRef = new WeakReference<Activity>(activity);
		inflater = LayoutInflater.from(activity);
		cards = MemCache.getDevelopmentCards(section);
	}

	public void refreshCards() {
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
		if (v == null) {
			v = inflater.inflate(R.layout.adapter_dev_card, null);
			holder = new ViewHolder(v, getItem(position));
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
			holder.updateView(getItem(position));
		}
		return v;
	}

	public class ViewHolder implements OnClickListener {

		private ImageView picImageView;
		private TextView titleTextView;
		private TextView subTitleTextView;
		private DevelopmentCard card;

		public ViewHolder(View v, DevelopmentCard card) {
			L.d(card);
			picImageView = (ImageView) v.findViewById(R.id.picImageView);
			titleTextView = (TextView) v.findViewById(R.id.title_textview);
			subTitleTextView = (TextView) v
					.findViewById(R.id.sub_title_textview);
			updateView(card);
		}

		public void updateView(DevelopmentCard item) {
			this.card = item;
			ArrayList<String> pics = item.getPics();
			boolean isPicAvailable = false;
			for (String aPic : pics) {
				Picasso.with(activityRef.get()).load(aPic).into(picImageView);
				picImageView.setTag(aPic);
				isPicAvailable = true;
				break;
			}

			picImageView.setOnClickListener(this);

			if (isPicAvailable) {
				picImageView.setVisibility(View.VISIBLE);
			} else {
				picImageView.setVisibility(View.GONE);
			}

			if (TextUtils.isEmpty(item.getTitle())) {
				titleTextView.setVisibility(View.GONE);
			} else {
				titleTextView.setText(item.getTitle());
				titleTextView.setVisibility(View.VISIBLE);
			}

			if (TextUtils.isEmpty(item.getSubTitle())) {
				subTitleTextView.setVisibility(View.GONE);
			} else {
				subTitleTextView.setText(item.getSubTitle());
				subTitleTextView.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.picImageView:
				showFullScreenPic((ImageView) v, card);
				break;

			default:
				break;
			}
		}
	}

	public void showFullScreenPic(ImageView v, DevelopmentCard card) {
		String url = (String) v.getTag();
		Intent intent = new Intent(activityRef.get(), PreviewActivity.class);
		intent.putExtra(BundleExtras.PIC_URL, url);
		intent.putExtra(BundleExtras.CARD, card);
		String title = activityRef.get().getResources()
				.getStringArray(R.array.section_headings)[section.ordinal()];
		intent.putExtra(BundleExtras.CATEGORY_TITLE, title);
		activityRef.get().startActivity(intent);
	}
}
