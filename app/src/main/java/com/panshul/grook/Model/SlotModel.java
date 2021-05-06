package com.panshul.grook.Model;

public class SlotModel {
    String slot;
    String isPresent;
    public SlotModel(){

    }

    public SlotModel(String slot, String isPresent) {
        this.slot = slot;
        this.isPresent = isPresent;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}
