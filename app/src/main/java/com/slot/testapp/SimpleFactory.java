package com.slot.testapp;

import com.slot.testapp.TB1.TB1Slot;
import com.slot.testapp.xiaomantou.XiaomantouSlot;

public class SimpleFactory {
    public static Customer createInstance(String type) {
        if ("xiaomantou".equals(type)) {
            return new XiaomantouSlot();
        }else if ("TB1".equals(type)) {
            return new TB1Slot();
        } else {
            throw new RuntimeException("type[" + type + "]类型不可识别，没有匹配到可实例化的对象！");
        }
    }
}
