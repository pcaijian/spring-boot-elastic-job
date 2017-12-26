package com.elastic.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额工具类,主要是金额的格式化,金额的加、减
 * 
 * @author 
 * 
 */
public class MoneyUtil {

    public static DecimalFormat fnum = new DecimalFormat("##0.00000000000000000000");

    /**
     * 格式化金额
     * 
     * @param value
     * @return
     */
    public static String formatMoney(String value) {
        if (value == null || value == "") {
            value = "0.00";
        }
        return fnum.format(new BigDecimal(value));
    }

    /**
     * 金额相加
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被加数
     * @return
     */
    public static String moneyAdd(String valueStr, String addStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return fnum.format(value.add(augend));
    }

    /**
     * 金额相加
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被加数
     * @return
     */
    public static BigDecimal moneyAdd(BigDecimal value, BigDecimal augend) {
        return value.add(augend);
    }

    /**
     * 金额相减
     * 
     * @param valueStr 基础值
     * @param minusValueStr 减数
     * @return
     */
    public static String moneySub(String valueStr, String minusStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(minusStr);
        return fnum.format(value.subtract(subtrahend));
    }

    /**
     * 金额相减
     * 
     * @param value 基础值
     * @param subtrahend 减数
     * @return
     */
    public static BigDecimal moneySub(BigDecimal value, BigDecimal subtrahend) {
        return value.subtract(subtrahend);
    }

    /**
     * 金额相乘
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被乘数
     * @return
     */
    public static String moneyMul(String valueStr, String mulStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     * 
     * @param value 基础值
     * @param mulValue 被乘数
     * @return
     */
    public static BigDecimal moneyMul(BigDecimal value, BigDecimal mulValue) {
        return value.multiply(mulValue);
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数,并四舍五入
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被乘数
     * @return
     */
    public static String moneydiv(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数,并四舍五入
     * 
     * @param value 基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneydiv(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 金额相除 <br/>
     * 精确小位小数,不进行四舍五入
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被乘数
     * @return
     */
    public static String moneydiv2(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_DOWN));
    }
    
    /**
     * 金额相除 <br/>
     * 精确小位小数,不进行四舍五入
     * 
     * @param value 基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneydiv2(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 2, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 金额相除 <br/>
     * 精确小位小数,不进行四舍五入
     * 
     * @param valueStr 基础值
     * @param minusValueStr 被乘数
     * @return
     */
    public static String moneydiv3(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 3, BigDecimal.ROUND_DOWN));
    }
    
    /**
     * 金额相除 <br/>
     * 精确小位小数,不进行四舍五入
     * 
     * @param value 基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneydiv3(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 值比较大小(采用>=符号判断) <br/>
     * 如果valueStr大于等于compValueStr,则返回true,否则返回false true 代表可用余额不足
     * 
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        // 0:等于 >0:大于 <0:小于
        int result = value.compareTo(compValue);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 值比较大小(采用>=符号判断) <br/>
     * 如果valueStr大于等于compValueStr,则返回true,否则返回false true 代表可用余额不足
     * 
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp(BigDecimal valueStr, BigDecimal compValueStr) {
        // 0:等于 >0:大于 <0:小于
        int result = valueStr.compareTo(compValueStr);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 值比较大小(采用>符号判断) <br/>
     * 如果valueStr大于compValueStr,则返回true,否则返回false true 代表可用余额不足
     * 
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp2(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        // 0:等于 >0:大于 <0:小于
        int result = value.compareTo(compValue);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 值比较大小(采用>符号判断) <br/>
     * 如果valueStr大于compValueStr,则返回true,否则返回false true 代表可用余额不足
     * 
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp2(BigDecimal valueStr, BigDecimal compValueStr) {
        // 0:等于 >0:大于 <0:小于
        int result = valueStr.compareTo(compValueStr);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金额乘以，省去小数点
     * 
     * @param valueStr 基础值
     * @return
     */
    public static String moneyMulOfNotPoint(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = fnum.format(value.multiply(mulValue));
        return fnum.format(value.multiply(mulValue)).substring(0, valueStr.length() - 3);
    }

    /**
     * 给金额加逗号切割
     * 
     * @param str
     * @return
     */
    public static String addComma(String str) {
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        } catch (Exception e) {
            return str;
        }

    }
    
    /*public static void main(String[] args) {
        BigDecimal bignum1 = new BigDecimal("9.25");  
        BigDecimal bignum2 = new BigDecimal("2");  
        BigDecimal bignum3 = null;  
          
        //加法  
        bignum3 =  bignum1.add(bignum2);       
        System.out.println("和 是：" + bignum3);  
          
        //减法  
        bignum3 = bignum1.subtract(bignum2);  
        System.out.println("差  是：" + bignum3);  
          
        //乘法  
        bignum3 = bignum1.multiply(bignum2);  
        System.out.println("积  是：" + bignum3);  
          
        //除法  
        bignum3 = bignum1.divide(bignum2);  
        System.out.println("商  是：" + bignum3);  
    }*/

}
