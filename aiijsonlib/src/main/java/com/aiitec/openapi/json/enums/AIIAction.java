package com.aiitec.openapi.json.enums;


/**
 * action枚举�?
 * @author Anthony
 *
 */
public enum AIIAction {

	/**默认(null) -1，不参与组包*/
	NULL(-1), 
	/**值为0*/
	ZERO(0), 
	/**值为1*/
	ONE(1), 
	/**值为2*/
	TWO(2), 
	/**值为3*/
	THREE(3), 
	/**值为4*/
	FOUR(4), 
	/**值为5*/
	FIVE(5), 
	/**值为6*/
	SIX(6),
	/**值为7*/
	SEVEN(7), 
	/**值为8*/
	EIGHT(8),
	/**值为9*/
	NIGHT(9),
	TEN(10),
	ELEVEN(11),
	TWELVE(12),
	THIRTEEN(13),
	FOURTEEN(14),
	FIFTEEN(15),
	SIXTEEN(16),
	SEVENTEEN(17),
	Eighteen(18),
	Nineteen(19);
	 
	private AIIAction(int value) {
	    this.value = value;
	}
	private int value;
	public int getValue() {
	    return value;
	}
//	
	public static int getValues(AIIAction status){
	    return status.getValue();
	}
	 /**
     * 通过int值获取枚举类，请使用这个方法valueOf(1)
     *  valueOf(String value) 因为不能重写，所以可以使用valueOf(“one“), 而不能使用valueOf("1")
     * @param value 传入的值
     * @return 对应的枚举类
     */
    public static AIIAction valueOf(int value) {
        for (int i = 0; i < values().length; i++) {
            if(values()[i].getValue() == value){
                return values()[i];
            }
        }
        return AIIAction.NULL;//默认返回0
    }
   
}
