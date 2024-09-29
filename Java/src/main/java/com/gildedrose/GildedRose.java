package com.gildedrose;

class GildedRose {
    //Constants instead of bloating functions
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured";

    Item[] items;

    //Init the items array
    public GildedRose(Item[] items) {
        this.items = items;
    }

    //Main method that goes over items and adjusts values accordingly
    public void newQuality() {
        for (Item item : items) {
            setItemQuality(item); //quality update
            setItemSellIn(item); //sellIn decrement (except for Sulfuras)
            ExpiredItems(item); //items with sellIn < 0
        }
    }

    //Updates the quality of the item based on its type
    private void setItemQuality(Item item) {
        //Special items like Aged Brie and Backstage Passes have different quality rules
        if (isSpecialItem(item)) {
            SpecialItems(item);
//        } else if (isConjuredItem(item)){
//            ConjuredItems(item);
//
        } else {
            //Regular items decrease in quality by 1 (if not Sulfuras)
            decrQuality(item);
        }
    }

    //Updates the sellIn value, except Sulfuras
    private void setItemSellIn(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn--; // Decrease sellIn by 1 unless the item is Sulfuras
        }
    }

    //items when sellIn < 0
    private void ExpiredItems(Item item) {
        if (item.sellIn < 0) {
            //Backstage passes die
            if (item.name.equals(BACKSTAGE_PASSES)) {
                item.quality = 0;
            }
            //Aged Brie just keeps winning
            else if (item.name.equals(AGED_BRIE)) {
                incrQuality(item);
            }
            //Other items decrease in quality after the sell-by date (except Sulfuras)
            else {
                decrQuality(item);
            }
        }
    }

    //Special items get different treatment (aged brie and backstage passes)
    private void SpecialItems(Item item) {
        if (item.name.equals(BACKSTAGE_PASSES)) {
            //Backstage passes increase quality the closer the sellin date, even more so when remaining days are 10 or less, or even more if 5 or less
            incrQuality(item);
            if (item.sellIn <= 10) {
                incrQuality(item);
            }
            if (item.sellIn <= 5) {
                incrQuality(item);
            }
        } else if (item.name.equals(AGED_BRIE)) {
            incrQuality(item);
        }
    }

//    private void ConjuredItems(Item item) {
//        decrQuality(item);
//    }

    //Increase the quality of item to max of 50
    private void incrQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    //Decrease the quality of an item, unless already 0 or the item is Sulfuras
    private void decrQuality(Item item) {
        if (item.quality > 0 && !item.name.equals(SULFURAS)) {
            item.quality--;
        }
        if (item.quality > 0 && isConjuredItem(item)){
            item.quality--;
        }
    }

    //Check if special item or not
    private boolean isSpecialItem(Item item) {
        return item.name.equals(AGED_BRIE) || item.name.equals(BACKSTAGE_PASSES);
    }

    private boolean isConjuredItem(Item item){
        return item.name.contains(CONJURED);
    }
}



