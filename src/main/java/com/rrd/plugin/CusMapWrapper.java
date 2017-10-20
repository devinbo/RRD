package com.rrd.plugin;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 对返回map结果集进行处理，将key全部转换为小写形式，并且将date数据转换为yyyy-MM-dd HH:mm:ss格式字符串
 * Created by Administrator on 2017/7/13.
 */
public class CusMapWrapper extends MapWrapper {

    public CusMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if(Pattern.matches("[A-Z]+", name)) { //当map 全部为大写的时候，那么将其设置为小写map
            return super.findProperty(name.toLowerCase(), useCamelCaseMapping); //设置map结果集全部为小写
        }else{
            return super.findProperty(name, useCamelCaseMapping);
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        //判断，如果时分秒都为00，那么返回yyyy-MM-dd形式
        if (value instanceof Date) {
            String strVal = DateFormatUtils.format((Date) value, "yyyy-MM-dd HH:mm:ss");
            strVal = strVal.endsWith("00:00:00") ? strVal.substring(0, 10) : strVal;
            value = strVal;
        }
        super.set(prop, value);
    }
}
