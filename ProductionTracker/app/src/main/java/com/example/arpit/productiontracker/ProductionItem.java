package com.example.arpit.productiontracker;

/**
 * Created by Arpit on 02-07-2018.
 */

public class ProductionItem {
    private String itemName;
    private int qtyProduced;
    private int qtyTarget;

    public ProductionItem(String itemName, int qtyProduced, int qtyTarget) {
        this.itemName = itemName;
        this.qtyProduced = qtyProduced;
        this.qtyTarget = qtyTarget;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public int getQtyProduced() {
        return qtyProduced;
    }
    public void setQtyProduced(int qtyProduced) {
        this.qtyProduced = qtyProduced;
    }
    public int getQtyTarget() {
        return qtyTarget;
    }
    public void setQtyTarget(int qtyTarget) {
        this.qtyTarget = qtyTarget;
    }
}
