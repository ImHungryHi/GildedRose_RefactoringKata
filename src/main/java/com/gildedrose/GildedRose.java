package com.gildedrose;

class GildedRose {
    public static final int MAX_QUALITY = 50;
    public static final int SULFURAS_QUALITY = 80;
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
    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.contains("Sulfuras")) {
                item.sellIn--;
                int qualityChangeMultiplier = checkSellInNegativeOrConjured(item);

                if (item.name.contains("Aged Brie")) {
                    item.quality += qualityChangeMultiplier;
                }
                else if (item.name.contains("Backstage passes")) {
                    calculateBackstagePassQuality(item, qualityChangeMultiplier);
                }
                // Default, generic item
                else if (item.quality > 0) {
                    item.quality -= qualityChangeMultiplier;
                }

                checkQualityConstraints(item);
            }
            // Sulfuras doesn't change, but keep tabs so that the quality remains 80
            else if (item.quality != SULFURAS_QUALITY) {
                item.quality = SULFURAS_QUALITY;
            }
        }
    }

    private int checkSellInNegativeOrConjured(Item item) {
        if (item.name.startsWith("Conjured") || item.sellIn < 0) {
            return 2;
        }

        return 1;
    }

    private void checkQualityConstraints(Item item) {
        if (item.quality > MAX_QUALITY) {
            item.quality = MAX_QUALITY;
        }
        else if (item.quality < 0) {
            item.quality = 0;
        }
    }

    private void calculateBackstagePassQuality(Item item, int qualityChangeMultiplier) {
        if (item.sellIn < 0) {
            item.quality = 0;
        }
        else if (item.sellIn < 6) {
            item.quality += 3 * qualityChangeMultiplier;
        } else if (item.sellIn < 11) {
            item.quality += 2 * qualityChangeMultiplier;
        }
        else {
            item.quality += qualityChangeMultiplier;
        }
    }
}
