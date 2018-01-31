package com.example;

import com.aiitec.openapi.json.JSON;
import com.aiitec.openapi.json.utils.JsonUtils;
import com.example.model.Address;
import com.example.model.Card;
import com.example.model.Region;
import com.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json 解析 例子
 * @author Anthony
 * createTime 2017/10/11 15:10
 */
public class ExampleClass {

    public static void main(String[] args){
        ExampleClass example = new ExampleClass();
//        String json = example.combination();
//        example.decomposition(json);
        example.mapTest();



    }

    private void mapTest() {

        Map map = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();

        map2.put("e", "ee2");
        map2.put("f", "ff2");

        map3.put("e", "ee2");
        map3.put("f", "ff2");

        map4.put("e", "ee2");
        map4.put("f", "ff2");
        List<Map> maps = new ArrayList<>();
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);

        map.put("a", "ss");
        map.put("b", "bb");
        map.put("c", "cc");
        map.put("d", map2);
        map.put("m", maps);
        String json = JSON.toJsonString(map);

        System.out.println(JsonUtils.formatJson(json));

        Map<String, Object> map1 = JSON.parseObject(json, Map.class);
//        Map<String, Object> map1 = JSON.parseMap(json);
        Map<String, Object> map5 = (Map<String, Object>) map1.get("d");
        List<Map> map2s = (List<Map>) map1.get("m");
        System.out.println("a:   "+map1.get("a"));
        System.out.println("e:   "+map5.get("e"));
        System.out.println("map2s:   "+map2s);

    }


    /**
     * 组包
     */
    private String combination() {

        Card card = new Card();
        int[] ids = {2, 4,7,8,5};
        card.setIds(ids);
        Address address = new Address();
        address.setId(32);
        address.setRegionId(440103);

        ArrayList<Region> regionInfo = new ArrayList<>();
        Region region1 = new Region();
        region1.setId(440000);
        region1.setName("广东省");
        regionInfo.add(region1);

        Region region2 = new Region();
        region2.setId(440100);
        region2.setName("广州市");
        regionInfo.add(region2);

        Region region3 = new Region();
        region3.setId(440103);
        region3.setName("荔湾区");
        regionInfo.add(region2);

        Region[] regions2 = new Region[1];
        regions2[0] = region1;
        address.setRegions2(regions2);
        address.setRegionInfo(regionInfo);
        card.setAddress(address);
        card.setCardName("张三");

        User user = new User();
        user.setName("张三");
        user.setSex(1);
        user.setMoney(113.22);
        user.setAttention(1);
        user.setHeight(68);
        card.setUser(user);

        String[] mobiles2 = {"13255664466","12345678977"};
        card.setMobiles2(mobiles2);
        String json = JSON.toJsonString(card);
        System.out.println(JsonUtils.formatJson(json));

        return json;
    }

    /**
     * 解包
     */
    private void decomposition(String content) {
        try {

            Card card = JSON.parseObject(content, Card.class);
            System.out.println("地址id:"+card.getAddress().getId()+"");
            System.out.println("地址regionId:"+card.getAddress().getRegionId()+"");
            List<Region> regionInfo = card.getAddress().getRegionInfo();
            if(regionInfo != null && regionInfo.size() > 0){
                System.out.println("省id:"+regionInfo.get(0).getId()+"");
                System.out.println("省名:"+regionInfo.get(0).getName()+"");
                if(regionInfo.size() > 1){
                    System.out.println("市id:"+regionInfo.get(1).getId()+"");
                    System.out.println("市名:"+regionInfo.get(1).getName()+"");
                }
                if(regionInfo.size() > 2){
                    System.out.println("区Id:"+regionInfo.get(2).getId()+"");
                    System.out.println("区名:"+regionInfo.get(2).getName()+"");
                }
            }
            User user = card.getUser();
            System.out.println("height:"+user.getHeight()+"");
            System.out.println("name:"+user.getName());
            System.out.println("Money:"+user.getMoney()+"");
            System.out.println("sex:"+user.getSex());
            if(card.getAddress().getRegions2() != null){
                System.out.println("Regions数组:"+card.getAddress().getRegions2()[0]);
            }
            if(card.getMobiles2()!= null && card.getMobiles2().length > 0){
                System.out.println("mobile数组:"+card.getMobiles2()[0]);
            }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "json数据格式不正确"+e.getMessage());
        }

    }


}
