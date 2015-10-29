package nadsonfernando.com.br.accessbusiness.interfaces;

import android.view.View;

/**
 * Created by nadsonfernando on 28/10/15.
 */
public interface RecyclerViewOnClickListenerHack {
    public abstract void onClickListener(View v, int position);
    public abstract void onLongPressClickListener(View v, int position);
}
