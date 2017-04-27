package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/4/25.
 */

public class EqBookTime extends BmobObject {
    private String EqID;
    private String Time7, Time8, Time9, Time10, Time11, Time12, Time13, Time14,
            Time15, Time16, Time17, Time18, Time19, Time20, Time21;

    public String getEqID() {
        return EqID;
    }

    public void setEqID(String eqID) {
        EqID = eqID;
    }

    public String getTime7() {
        return Time7;
    }

    public void setTime7(String time7) {
        Time7 = time7;
    }

    public String getTime8() {
        return Time8;
    }

    public void setTime8(String time8) {
        Time8 = time8;
    }

    public String getTime9() {
        return Time9;
    }

    public void setTime9(String time9) {
        Time9 = time9;
    }

    public String getTime10() {
        return Time10;
    }

    public void setTime10(String time10) {
        Time10 = time10;
    }

    public String getTime11() {
        return Time11;
    }

    public void setTime11(String time11) {
        Time11 = time11;
    }

    public String getTime12() {
        return Time12;
    }

    public void setTime12(String time12) {
        Time12 = time12;
    }

    public String getTime13() {
        return Time13;
    }

    public void setTime13(String time13) {
        Time13 = time13;
    }

    public String getTime14() {
        return Time14;
    }

    public void setTime14(String time14) {
        Time14 = time14;
    }

    public String getTime15() {
        return Time15;
    }

    public void setTime15(String time15) {
        Time15 = time15;
    }

    public String getTime16() {
        return Time16;
    }

    public void setTime16(String time16) {
        Time16 = time16;
    }

    public String getTime17() {
        return Time17;
    }

    public void setTime17(String time17) {
        Time17 = time17;
    }

    public String getTime18() {
        return Time18;
    }

    public void setTime18(String time18) {
        Time18 = time18;
    }

    public String getTime19() {
        return Time19;
    }

    public void setTime19(String time19) {
        Time19 = time19;
    }

    public String getTime20() {
        return Time20;
    }

    public void setTime20(String time20) {
        Time20 = time20;
    }

    public String getTime21() {
        return Time21;
    }

    public void setTime21(String time21) {
        Time21 = time21;
    }

    public String getTimeOfN(int i) {
        String s = "error";
        switch (i) {
            case 0:
                s = getTime7();
                break;
            case 1:
                s = getTime8();
                break;
            case 2:
                s = getTime9();
                break;
            case 3:
                s = getTime10();
                break;
            case 4:
                s = getTime11();
                break;
            case 5:
                s = getTime12();
                break;
            case 6:
                s = getTime13();
                break;
            case 7:
                s = getTime14();
                break;
            case 8:
                s = getTime15();
                break;
            case 9:
                s = getTime16();
                break;
            case 10:
                s = getTime17();
                break;
            case 11:
                s = getTime18();
                break;
            case 12:
                s = getTime19();
                break;
            case 13:
                s = getTime20();
                break;
            case 14:
                s = getTime21();
                break;
            default:
                break;
        }
        return s;
    }

    public void setTimeOfN(int i, String s) {
        switch(i) {
            case 0:
                setTime7(s);
                break;
            case 1:
                setTime8(s);
                break;
            case 2:
                setTime9(s);
                break;
            case 3:
                setTime10(s);
                break;
            case 4:
                setTime11(s);
                break;
            case 5:
                setTime12(s);
                break;
            case 6:
                setTime13(s);
                break;
            case 7:
                setTime14(s);
                break;
            case 8:
                setTime15(s);
                break;
            case 9:
                setTime16(s);
                break;
            case 10:
                setTime17(s);
                break;
            case 11:
                setTime18(s);
                break;
            case 12:
                setTime19(s);
                break;
            case 13:
                setTime20(s);
                break;
            case 14:
                setTime21(s);
                break;
            default:
                break;
        }
    }

}
