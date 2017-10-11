# AiiJson 库  
本库主要作用是解析和组装json,用途与Gson、fastJson 差不多，性能略逊于它们，存在的目的是为了适应本公司的特殊情况的json,
本公司的json 数组的结构是 
'{"datas":  
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
'
而正常的json并没有中间的key ,
此外的解析过程中各种异常已经捕获，能够更好的防御服务端返回错的数据问题。
