package ua.pp.danielyuzek.simple_schedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.models.Lesson;
import ua.pp.danielyuzek.simple_schedule.utilities.Const;

/**
 * Created by iarychernko on 17.06.16.
 */
public class LessonsAdapter extends BaseAdapter {

    private Context mContext;

    private List<Lesson> lessons = new ArrayList<>();

    public LessonsAdapter(Context context, List<Lesson> lessons) {
        this.mContext = context;
        this.lessons = lessons;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Lesson getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lessons.get(position).getOrdering();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater  inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout, null, true);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.name);
            holder.place = (TextView) rowView.findViewById(R.id.place);
            holder.teacher = (TextView) rowView.findViewById(R.id.teacher);
            holder.time = (TextView) rowView.findViewById(R.id.time);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        Lesson lesson = getItem(position);
        holder.name.setText(lesson.getOrdering() + ". " + lesson.getName());
        holder.place.setText(lesson.getPlace());
        if (lesson.getTeacher() != null) {
            holder.teacher.setText(lesson.getTeacher());
            holder.teacher.setVisibility(View.VISIBLE);
        } else {
            holder.teacher.setVisibility(View.GONE);
        }

        holder.time.setText(Const.LESSONS_TIME[lesson.getOrdering()]);

        return rowView;
    }

    static class ViewHolder {
        public TextView name;
        public TextView place;
        public TextView teacher;
        public TextView time;
    }
}
