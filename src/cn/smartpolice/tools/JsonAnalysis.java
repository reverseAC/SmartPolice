package cn.smartpolice.tools;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONObject;

public class JsonAnalysis {

    final static String DEBUG = "JsonAnalysis";

	/**
	 * ��ȡjson��
	 * 
	 * @param message
	 * @return json String
	 */
	public String getJsonString(Map map) { 

		JSONObject jsonObject = new JSONObject();
		if (map == null) {

			System.out.println(DEBUG + "getJsonString the param is null");
			return null;
		}
		jsonObject.putAll(map);
		return jsonObject.toString();

	}
	/**
	 * ��ȡjson���е�����ֵ
	 * @param jsonString
	 * @param key
	 * @return the value of the key 
	 * if it don't have the value  return null
	 */
	public String getValue(String jsonString, String key) {
		if(jsonString == null || key == null){
			
			return null;
		}
		if (jsonString.contains(key)) {
			//转化为json
			 JSONObject jobj = JSONObject.fromObject(jsonString) ; 
			 /*System.out.println("JSONObject:"+jobj);*/
			 //取的key对应的值
			return jobj.getString(key);

		} else {

			
			return null;
		}

		
	}
	/**
	 * ͨ���װ��ʵ���ȡjson����
	 * @param obj
	 * @return
	 */
	
	public String getJsonArray(Object obj){
		String Jmsg =null;
		JsonMessage  d =null;
		if(obj instanceof JsonMessage){
			
			d =(JsonMessage)obj;
			
		}
		
		if(obj == null|| d==null){
			
			return null;
		}
		JSONArray ja =new JSONArray();
		ja.add(d);
		Jmsg =ja.toJSONString();
		
		return Jmsg;
		
		
		
	}
	/**
	 * �Ѷ���ת����json
	 * @param obj bean ����
	 * @return
	 */
	public String getJsonByObject(Object obj){
		String Jmsg =null;
		
		if(obj != null){
			JSONObject jb =JSONObject.fromObject(obj);
			Jmsg =jb.toString();
		}
		return Jmsg;
		
		
	}

}
