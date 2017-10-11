package com.example;

import com.aiitec.openapi.json.JSON;
import com.example.model.Address;
import com.example.model.Card;
import com.example.model.Region;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;
/**
 * json 解析 例子
 * @author Anthony
 * createTime 2017/10/11 15:10
 */
public class ExampleClass {

    public static void main(String[] args){
        ExampleClass example = new ExampleClass();
        String json = example.combination();
        example.decomposition(json);
    }

    /**
     * 组包
     */
    private String combination() {

        Card card = new Card();

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
        String json = JSON.toJsonString(card);
        System.out.println(json);

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

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "json数据格式不正确"+e.getMessage());
        }

    }


}
