package com.gildedrose;

class GildedRose {
    public static final int MAX_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    // Rules to play by:
    //  - SellIn -> days left to sell product
    //  - Quality -> How valuable the item is
    //  - At the end of the day, quality is updated for all items
    //     - 0 >= Quality > 50
    //     - SellIn negative -> quality degrades twice as fast
    //     - [Conjured] -> quality degrades twice as fast (OR stmt in tandem with above)
    //     - [Aged Brie] -> quality increases instead of decreasing
    //     - [Sulfuras] -> no SellIn applies, quality remains as is (=80)
    //     - [Backstage passes] -> quality increases instead of decreasing; increasing by 2
    //          if SellIn < 11 and by 3 if SellIn < 6; drops to 0 when SellIn is negative
    //-------- CURRENT IN CODE --------
    //  - [Aged Brie] -> increment quality if it drops below 50, once if sellIns are >= 0, twice otherwise
    //  - [Backstage passes] -> reset quality to 0 if sellIn is negative, else increment twice if quality
    //       is under 50 and sellIn is less than 11, once more when sellIn is 5 or less
    //  - [Sulfuras] -> don't decrement sellIn, increment quality by 1 if under 50
    //  - Everything else -> decrement quality by 1 if >= 0, once more if sellIn is negative
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
