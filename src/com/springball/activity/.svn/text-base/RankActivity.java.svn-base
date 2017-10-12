package com.springball.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springball.base.BaseActivity;
import com.springball.util.DataBaseManager;

/**
 * 排行榜页面
 * @author cjxiao
 *
 */
public class RankActivity extends BaseActivity {

	private ListView listView;
	private List<RankItem> list;
	private RankAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_layout);
		init(this);
	}
	
	private void init(Context context){
		listView = (ListView)findViewById(R.id.rank_list);
		list = new ArrayList<RankItem>();
		DataBaseManager dbMgr = new DataBaseManager(context);
		//查询出所有的纪录并降序排列
		Cursor cursor = dbMgr.queryDatas(new String[]{"*"}, null, null, null, null, "score desc,time desc");
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int score = cursor.getInt(cursor.getColumnIndex("score"));
			list.add(new RankItem(name, score));
		}
		adapter = new RankAdapter(list);
		listView.setAdapter(adapter);
	}
	
	class RankAdapter extends BaseAdapter{
		
		RankAdapter(List<RankItem> items){
			list = items;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder ;
			 RankItem item = list.get(position);
			 if(convertView == null){
				 holder = new ViewHolder();
				 convertView = LayoutInflater.from(RankActivity.this).inflate(R.layout.rank_list_item, null);
				 holder.nameTv = (TextView)convertView.findViewById(R.id.name_item);
				 holder.scoreTv = (TextView)convertView.findViewById(R.id.score_item);
				 convertView.setTag(holder);
			 }else{
				 holder = (ViewHolder) convertView.getTag();
			 }
			 holder.nameTv.setText(item.getName());
			 holder.scoreTv.setText(item.getScore()+"");
			 return convertView;
		}
		
	}
	
	class RankItem{
		String name;
		int score;
		RankItem(String n,int s){
			name = n;
			score = s;
		}
		String getName(){
			return name;
		}
		int getScore(){
			return score;
		}
	}
	
	class ViewHolder{
		TextView nameTv;
		TextView scoreTv;
	}
}
