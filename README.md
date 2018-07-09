# AiiJson 库  
本库主要作用是解析和组装json,用途与Gson、fastJson 差不多，性能略逊于它们，存在的目的是为了适应本公司的特殊情况的json,
本公司的json 数组的结构是   
```
{"datas":  
    {  
        "data":  
         {  
            "name":"zhangsan",  
            "id":1  
         }  
    },  
    {  
        "data":  
        {  
             "name":"lisi",  
             "id":2  
        }  
    },  
    {    
    "data":  
        {  
             "name":"wangwu",  
             "id":3  
        }  
    }  
}
``` 
而正常的json并没有中间的key ,
```
{"datas":  
    {  
         "name":"zhangsan",  
         "id":1   
    },  
    {  
        "name":"lisi",  
        "id":2   
    },  
    {    
        "name":"wangwu",  
        "id":3   
    }  
}
``` 
此外的解析过程中各种异常已经捕获，能够更好的防御服务端返回错的数据问题。  

### 使用方法  
```
//组包：
public String JSON.toJsonString(T t)
//解包：
public static <T> T parseObject(String json, Class<T> clazz) 
public static <T> T parseObject(JSONObject json, T t)
public static <T> T parseObject(String json, Class<T> clazz)
public static <T> T parseObject(JSONObject json, T t) 
public static <T> List<T> parseArray(String json, Class<T> entityClazz) 
```
如果定义的变量名与组包的key名不一致的，可以用注解JSONField(name="组包key")来解决，如：
```
    /**
    * 协议名称
    */
    @JSONField(name="n")
    protected String namespace;
    /**
    * 缓存时间戳
    */
    @JSONField(name="t")
    protected String timestampLatest;
```

如果是List集合，而我们的json格式会 在regionInfo 里面还有一层region对象，那么就要定义entityName="region"，如果集合变量是orders 里层是order， 或者集合变量是goodses里层是goods的话则不需要加注解，因为设计时会默认把集合变量后面有一个s的变量去掉s当成里层的key，后面是ses的去掉es当成里层的key。  
```
    @JSONField(entityName="region")
    protected List<Region> regionInfo;
``` 
json 样式
```
    {
        "regionInfo": [
            {
                "region": {
                    "id": 440100,
                    "name": "广东省"
                }
            },
            {
                "region": {
                    "id": 440100,
                    "name": "广州市"
                }
            },
            {
                "region": {
                    "id": 440103,
                    "name": "荔湾区"
                }
            }
        ]
    }
```
其实现在很多json是集合没有里面那层的就像上面右边的图一样，但是我们公司已经定义这种规范很久了不能随便改变，  
那么json解析的设计当然要考虑两种情况，所以就有里CombinationType类，
```
如果
JSON.combinationType = CombinationType.AII_STYLE;
// 则是我们公司的规范，也就是有中间的key "region";
JSON.combinationType = CombinationType.NORMAL;
```
 //则是其他公司通用规范，也就是没有中间的key "region";，但是这个我写成了静态的，可用统一改，如果需要单独对某一次解析更改，则在解析方法后面加combinationType 。
```
parseObject(json, Class<T> clazz, CombinationType.NORMAL) 
```
有些字段我们是用来做其他事情的，不需要组包则加注解@JSONField(notCombination=true)，如  
```
/**
* 是否开启加密
* 默认开启
*/
@JSONField(notCombination=true)
protected static boolean isOpenMd5 = true; 
```
有些字段-1也要组包，那么就加注解@JSONField(isForcedCombination=true)强制组包，如
```
@JSONField(isForcedCombination=true)
private long userId = -1;
```

### 字段加密
可以在字段上加注解 @JSONField(isPassword=true)

```
 @JSONField(isPassword=true)
 private String password

 user.setPassword("123456")
 ```

 组成json时会变成加密后的值
 默认加密方式 md5(saltingStr + md5(pasword))
 需要更改可以这样写
 ```
 JSON.saltingPassword = false
 //设置加盐秘钥
 Encrypt.setSaltingStr("sfdsfdsgcvcvv")
 //自定义加密方法
 Encrypt.setCustomAlgorithm { pasword, saltingStr ->
       return@setCustomAlgorithm "你的加密方式"
 }
 //不设置时，如果 JSON.saltingPassword = true 默认 md5(saltingStr + md5(pasword))
 如果  JSON.saltingPassword = false 默认 md5(pasword)
 ```

### 设置是否排序和自定义排序

```
    //是否需要排序
    JSON.needSort = true;
    //设置自定义排序
    CombinationUtil.setOnFieldComparatorListener(new CombinationUtil.OnFieldComparatorListener() {
            @Override
            public int onFieldComparator(String fieldName1, String fieldName2) {
                //自己定义的排序方式
                return fieldName1.compareToIgnoreCase(fieldName2);
            }
        });
```

### 引用方式

``` 
dependencies {
    ...  
    compile 'com.aiitec.aiijson:aiijsonlib:1.0.5'
}
```

更新日志：
1.0.3 增加对数组和Map的支持，Map只支持里面装常用数据类型，不支持Map装自定义对象
更新时间 2018-01-31
1.0.4 增加字段的排序
更新时间 2018-05-14
1.0.5 增加排序可选，和自定义排序，默认不排序
更新时间 2018-07-09

### 联系交流
QQ 544897948
微信 544897948

```
Copyright 2017 AiiJson

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
