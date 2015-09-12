package alberto.example.com.emptylistview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alberto Velo Carrasco on 12/09/15.
 */
public class EmptyListAdapter extends RecyclerView.Adapter<EmptyListAdapter.CustomViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int CARD_VIEW = 2;
    List<Integer> mData;
    boolean showEmptyView;
    Context mContext;

    public EmptyListAdapter(Context context) {
        mContext = context;
        setHasStableIds(true);
    }

    public void setData(List<Integer> data) {
        if (data == null) {
            showEmptyView = true;
            mData = Arrays.asList(1);
        } else {
            showEmptyView = false;
            mData = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (showEmptyView) {
            return EMPTY_VIEW;
        } else {
            return CARD_VIEW;
        }
    }

    @Override
    public long getItemId(int position) {
        if (isDataNotEmpty() && mData.size() > position) {
            return mData.get(position);
        } else {
            return 0;
        }
    }

    @Override
    public EmptyListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_VIEW:
                return new EmptyViewHolder(inflateLayout(parent, R.layout.empty_view));
            default:
                return new CardViewHolder(inflateLayout(parent, R.layout.list_item));
        }
    }

    private View inflateLayout(ViewGroup parent, int resourceId) {
        return LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
    }

    @Override
    public void onBindViewHolder(
            CustomViewHolder holder, int position) {
        if (isDataNotEmpty() && mData.size() > position) {
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).setNumber(mData.get(position));
            }
        }

    }

    private boolean isDataNotEmpty() {
        return mData != null && mData.size() > 0;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    abstract class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CardViewHolder extends CustomViewHolder {

        TextView mNumber;
        CardView mCardView;

        public CardViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mNumber = (TextView) itemView.findViewById(R.id.number);
        }

        public void setNumber(int number) {
            mNumber.setText(String.valueOf(number));
            mCardView.setCardBackgroundColor(getCardColor(number));
        }

        private int getCardColor(int number) {
            switch (number % 3) {
                case 0:
                    return mContext.getResources().getColor(
                            android.R.color.holo_orange_dark);
                case 1:
                    return mContext.getResources().getColor(
                            android.R.color.holo_blue_dark);
                default:
                    return mContext.getResources().getColor(
                            android.R.color.holo_red_dark);
            }
        }
    }

    class EmptyViewHolder extends CustomViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
