package com.gildedrose;

class GildedRose {
    public static final int MAX_QUALITY = 50;
    public static final int SULFURAS_QUALITY = 80;

    private static final int MINIMAL_QUALITY = 0;
    private static final int MINIMAL_SELLIN_DAYS = 0;
    private static final int NORMAL_DEGRADING = 1;
    private static final int RAPID_DEGRADING = 2;
    private static final int RARE_TICKET_DAYS_LEFT = 11;
    private static final int LEGENDARY_TICKET_DAYS_LEFT = 6;
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes";
    private static final String CONJURED = "Conjured";
    private static final String SULFURAS = "Sulfuras";

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
            if (!item.name.contains(SULFURAS)) {
                item.sellIn--;
                int qualityChangeMultiplier = checkSellInNegativeOrConjured(item);

                if (item.name.contains(AGED_BRIE)) {
                    item.quality += qualityChangeMultiplier;
                }
                else if (item.name.contains(BACKSTAGE_PASSES)) {
                    calculateBackstagePassQuality(item, qualityChangeMultiplier);
                }
                // Default, generic item
                else if (item.quality > MINIMAL_QUALITY) {
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
        if (item.name.startsWith(CONJURED) || item.sellIn < MINIMAL_SELLIN_DAYS) {
            return RAPID_DEGRADING;
        }

        return NORMAL_DEGRADING;
    }

    private void checkQualityConstraints(Item item) {
        if (item.quality > MAX_QUALITY) {
            item.quality = MAX_QUALITY;
        }
        else if (item.quality < MINIMAL_QUALITY) {
            item.quality = MINIMAL_QUALITY;
        }
    }

    private void calculateBackstagePassQuality(Item item, int qualityChangeMultiplier) {
        if (item.sellIn < MINIMAL_SELLIN_DAYS) {
            item.quality = MINIMAL_QUALITY;
        }
        else if (item.sellIn < LEGENDARY_TICKET_DAYS_LEFT) {
            item.quality += 3 * qualityChangeMultiplier;
        } else if (item.sellIn < RARE_TICKET_DAYS_LEFT) {
            item.quality += 2 * qualityChangeMultiplier;
        }
        else {
            item.quality += qualityChangeMultiplier;
        }
    }
}
